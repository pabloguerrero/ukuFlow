package org.eclipse.bpmn2.modeler.core.features;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeService;

public class RouteSolver {

	protected final static IGaService gaService = Graphiti.getGaService();
	protected static final IPeService peService = Graphiti.getPeService();
	static final int topMargin = 50;
	static final int bottomMargin = 50;
	static final int leftMargin = 50;
	static final int rightMargin = 50;

	IFeatureProvider fp;
	List<ContainerShape> allShapes;
	Shape source; 
	Shape target;
	int top, left, bottom, right;
	RoutingNet verticalNet;
	RoutingNet horizontalNet;
	private boolean rotate = false;
	
	/**
	 * RouteSolver constructor.
	 * 
	 * @param fp - Graphiti Feature Provider
	 * @param allShapes - a list of all shapes that are considered in the routing solution.
	 */
	public RouteSolver(IFeatureProvider fp, List<ContainerShape> allShapes) {
		this.fp = fp;
		this.allShapes = new ArrayList<ContainerShape>();
		this.allShapes.addAll(allShapes);
		initialize();
	}
	
	public boolean solve(Shape source, Shape target) {
		this.source = source;
		this.target = target;
		List< List<RoutingLane> > verticalSolutions;
		List< List<RoutingLane> > horizontalSolutions;
		
		verticalNet.eraseLanes();
		horizontalNet.eraseLanes();
		
		verticalSolutions = verticalNet.findSolutions(source, target);
		verticalNet.drawLanes();
		verticalNet.drawConnections();
//		if (verticalSolutions.size()>0) {
//			for (int i=0; i<verticalSolutions.size(); ++i) {
//				verticalNet.drawSolution(verticalSolutions.get(i), i);
//				if (i>16)
//					break;
//			}
//		}
		
//		horizontalSolutions = horizontalNet.findSolutions(source, target);
//		horizontalNet.drawLanes();
//		horizontalNet.drawConnections();
//		if (horizontalSolutions.size()>0) {
//			horizontalNet.drawSolution(horizontalSolutions.get(0));
//		}
		return true;
	}
	
	public boolean initialize() {
		
		if (allShapes.size()<2)
			return false;

		rotate = false;
		
		verticalNet = new RoutingNet(fp);
		Rectangle r = calculateDiagramBounds();
		sortAllShapes();
		top = r.y;
		left = r.x;
		bottom = top + r.height;
		right = left + r.width;
		calculateRoutingNet(verticalNet);
		verticalNet.link();

		rotate = true;
		
		horizontalNet = new RoutingNet(fp);
		r = calculateDiagramBounds();
		sortAllShapes();
		top = r.y;
		left = r.x;
		bottom = top + r.height;
		right = left + r.width;
		calculateRoutingNet(horizontalNet);
		horizontalNet.link();

		rotate = false;

		return true;
	}

	protected Rectangle calculateDiagramBounds() {
		// find bounding rectangle that contains all shapes
		int left = Integer.MAX_VALUE;
		int right = Integer.MIN_VALUE;
		int top = Integer.MAX_VALUE;
		int bottom = Integer.MIN_VALUE;
		int x, y;
		for (ContainerShape s : allShapes) {
			Rectangle r = getBounds(s);
			x = r.x;
			if (x < left)
				left = x;
			x = r.right();
			if (x > right)
				right = x;
			y = r.y;
			if (y < top)
				top = y;
			y = r.bottom();
			if (y > bottom)
				bottom = y;
		} 

		left -= leftMargin;
		top -= topMargin;
		bottom += bottomMargin;
		right += rightMargin;
		
		return new Rectangle(left, top, right-left, bottom-top);
	}
	
	protected void calculateRoutingNet(RoutingNet net) {

		net.add(left, top, leftMargin, bottom-top);
		for (int i=0; i<allShapes.size(); ++i) {
			ContainerShape shape = allShapes.get(i);
			if (GraphicsUtil.getDebugText(shape).contains("Task_1")) {
				GraphicsUtil.debug = true;
			}
			else
				GraphicsUtil.debug = false;

			// get bounding rectangle for current shape
			Rectangle shapeBounds = getBounds(shape);
			// The rectangular region below the current shape will be sliced
			// into smaller rectangles (a.k.a. "Routing Lanes"). To do this we'll
			// create a horizontal slicer that keeps track of the location
			// and width of each void defined by the top edge of the current
			// shape, and the left/right edges of the shapes below it.
			Slice slice = new Slice(shapeBounds.x, shapeBounds.right());
			List<ContainerShape> below = getShapesBelow(shape);
			for (ContainerShape shapeBelow : below) {
				Rectangle shapeBelowBounds = getBounds(shapeBelow);
				if (slice.remove(shapeBelowBounds.x, shapeBelowBounds.right()) == 0)
					break;
			}
			
			List<Integer> cuts = slice.getCuts();
			int c1 = cuts.get(0);
			for (int ic=1; ic<cuts.size(); ++ic) {
				int c2 = cuts.get(ic);
				int x = c1;
				int y = shapeBounds.y+shapeBounds.height;
				int w = c2-c1;
				int h = Integer.MIN_VALUE;
				
				for (ContainerShape shapeBelow : below) {
					Rectangle shapeBelowBounds = getBounds(shapeBelow);
					if (shapeBelowBounds.x<=c1 && c1<shapeBelowBounds.right()) {
						h = shapeBelowBounds.y - shapeBounds.y - shapeBounds.height;
						break;
					}
				}
				if (h==Integer.MIN_VALUE) {
					h = bottom - shapeBounds.y - shapeBounds.height;
				}
				
				net.add(x, y, w, h);
				c1 = c2;
			}

			// calculate lanes above the current shape
			slice = new Slice(shapeBounds.x, shapeBounds.right());
			List<ContainerShape> above = getShapesAbove(shape);
			for (ContainerShape shapeAbove : above) {
				Rectangle shapeAboveBounds = getBounds(shapeAbove);
				if (slice.remove(shapeAboveBounds.x, shapeAboveBounds.right()) == 0)
					break;
			}

			// Now we'll do the same thing for lane above the current shape,
			// but only those that extend all the way to the top of the diagram.
			// We don't want any overlapping lane.
			cuts = slice.getCuts();
			c1 = cuts.get(0);
			for (int ic=1; ic<cuts.size(); ++ic) {
				int c2 = cuts.get(ic);
				int x = c1;
				int y = top;
				int w = c2-c1;
				int h = Integer.MIN_VALUE;
				
				for (ContainerShape shapeAbove : above) {
					Rectangle shapeAboveBounds = getBounds(shapeAbove);
					if (shapeAboveBounds.x<=c1 && c1<shapeAboveBounds.right()) {
						h = shapeAboveBounds.y - shapeBounds.y - shapeBounds.height;
						break;
					}
				}
				if (h==Integer.MIN_VALUE) {
					h = shapeBounds.y - top;
					// only add the lane if there is no shape above this region.
					net.add(x, y, w, h);
				}
				
				c1 = c2;
			}

			addTrailingAisle(net, shape);
		}
		net.add(right-rightMargin, top, rightMargin, bottom-top);
		
		// rotate the horizontal lane nodes
		net.rotate(rotate);
	}
	
	protected void addTrailingAisle(RoutingNet net, ContainerShape shape) {
		Rectangle shapeBounds = getBounds(shape);
		int x = shapeBounds.right();
		int size = allShapes.size();
		for (int i=0; i<size; ++i) {
			ContainerShape s = allShapes.get(i);
			if (s!=shape) {
				Rectangle b = getBounds(s);
				if (b.x<=x && x<b.right())
					return;
			}
		}
		
		for (int i=0; i<size; ++i) {
			ContainerShape s = allShapes.get(i);
			if (s==shape) {
				for (int n=i+1; n<size; ++n) {
					ContainerShape nextShape = allShapes.get(n);
					Rectangle nextShapeBounds = getBounds(nextShape);
					if (nextShapeBounds.x>shapeBounds.right()) {
						net.add(shapeBounds.right(),top, nextShapeBounds.x - shapeBounds.right(), bottom-top);
						return;
					}
				}
			}
		}
	}

	protected List<ContainerShape> getShapesBelow(ContainerShape shape) {
		final Rectangle bounds = getBounds(shape);
		List<ContainerShape> shapes = new ArrayList<ContainerShape>();
		for (ContainerShape s : allShapes) {
			if (s!=shape) {
				Rectangle b = getBounds(s);
				if (b.x>bounds.right())
					break;
				int d = b.y - bounds.y;
				if (
						d>=0 && (
							(bounds.x<=b.x && b.x<=bounds.right()) ||
							(bounds.x<=b.right() && b.right()<=bounds.right()) ||
							(b.x<=bounds.x && bounds.right()<=b.right())
						)
							
				) {
					shapes.add(s);
				}
					
			}
		}
		// sort the rectangles by increasing distance from the bottom of the given shape
		Collections.sort(shapes, new Comparator<ContainerShape>() {
			@Override
			public int compare(ContainerShape s1, ContainerShape s2) {
				Rectangle b1 = getBounds(s1);
				Rectangle b2 = getBounds(s2);
				int d1 = b1.y - bounds.bottom();
				int d2 = b2.y - bounds.bottom();
				int i = d1 - d2;
				if (i==0) {
					i = b1.x - b2.x;
				}
				return i;
			}
		});
		
		for (int i=0; i<shapes.size(); ++i) {
			ContainerShape s = shapes.get(i);
			Rectangle b = getBounds(s);
			if (b.x<=bounds.x && b.right()>=bounds.right()) {
				++i;
				while (i<shapes.size()) {
					shapes.remove(i);
				}
			}
		}
		return shapes;
	}

	protected List<ContainerShape> getShapesAbove(ContainerShape shape) {
		final Rectangle bounds = getBounds(shape);
		List<ContainerShape> shapes = new ArrayList<ContainerShape>();
		for (ContainerShape s : allShapes) {
			if (s!=shape) {
				Rectangle b = getBounds(s);
				if (b.x>bounds.right())
					break;
				int d = b.y - bounds.y;
				if (
						d<=0 && (
							(bounds.x<=b.x && b.x<=bounds.right()) ||
							(bounds.x<=b.right() && b.right()<=bounds.right()) ||
							(b.x<=bounds.x && bounds.right()<=b.right())
						)
							
				) {
					shapes.add(s);
				}
					
			}
		}
		// sort the rectangles by increasing distance from the top of the given shape
		Collections.sort(shapes, new Comparator<ContainerShape>() {
			@Override
			public int compare(ContainerShape s1, ContainerShape s2) {
				Rectangle b1 = getBounds(s1);
				Rectangle b2 = getBounds(s2);
				int d1 = b1.y - bounds.y;
				int d2 = b2.y - bounds.y;
				int i = d1 - d2;
				if (i==0) {
					i = b1.x - b2.x;
				}
				return i;
			}
		});
		
		for (int i=0; i<shapes.size(); ++i) {
			ContainerShape s = shapes.get(i);
			Rectangle b = getBounds(s);
			if (b.x<=bounds.x && b.right()>=bounds.right()) {
				++i;
				while (i<shapes.size()) {
					shapes.remove(i);
				}
			}
		}
		return shapes;
	}
	
	class Slice {
		protected Slice parent;
		protected int left, right;
		List<Integer> cuts = new ArrayList<Integer>();
		List<Slice> children = new ArrayList<Slice>();
		
		public Slice(int left, int right) {
			this(null,left,right);
		}
		
		protected Slice(Slice parent, int left, int right) {
			this.parent = parent;
			this.left = left;
			this.right = right;
			cuts.add(left);
			cuts.add(right);
		}
		
		public int remove(int l, int r) {
			if (r<left || right<l) {
				for (Slice s : children) {
					s.remove(l, r);
				}
			}
			else if (l<=left && left<=r && r<=right) {
				for (Slice s : children) {
					s.remove(l, r);
				}
				cut(left);
				left = r;
			}
			else if (l<=left && right<=r) {
				for (Slice s : children) {
					s.remove(l, left);
					s.remove(right, r);
				}
				cut(left);
				cut(right);
				right = left;
			}
			else if (l>left && r<right) {
				children.add(new Slice(this,left,l));
				children.add(new Slice(this,r,right));
				cut(left);
				left = l;
				cut(right);
				right = r;
			}
			else if (left<=l && l<=right && r>=right) {
				for (Slice s : children) {
					s.remove(r, right);
				}
				cut(right);
				right = l;
			}
			return length();
		}
		
		public int length() {
			int length = right - left;
			for (Slice s : children) {
				length += s.length();
			}
			return length;
		}
		
		protected void cut(int point) {
			if (!cuts.contains(point))
				cuts.add(point);
		}
		
		public List<Integer> getCuts() {
			cut(left);
			cut(right);
			for (Slice s : children) {
				for (Integer p : s.getCuts())
					cut(p);
			}
			if (parent==null) {
				Collections.sort(cuts);
			}
			return cuts;
		}
	}
	
	protected void sortAllShapes() {
		Collections.sort(allShapes, new Comparator<ContainerShape>() {

			@Override
			public int compare(ContainerShape s1, ContainerShape s2) {
				Rectangle r1 = getBounds(s1);
				Rectangle r2 = getBounds(s2);
				int i = r1.x - r2.x;
				if (i==0) {
					i = r1.y - r2.y;
				}
				return i;
			}
		});
	}

	private Rectangle getBounds(ContainerShape shape) {
		return RoutingNet.getBounds(rotate,shape);
	}
}
