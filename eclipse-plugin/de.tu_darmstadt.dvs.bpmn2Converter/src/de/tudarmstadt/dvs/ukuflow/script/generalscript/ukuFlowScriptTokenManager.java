/* Generated By:JavaCC: Do not edit this line. ukuFlowScriptTokenManager.java */
package de.tudarmstadt.dvs.ukuflow.script.generalscript;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.expression.*;
import de.tudarmstadt.dvs.ukuflow.script.generalscript.functions.*;
import de.tudarmstadt.dvs.ukuflow.tools.debugger.BpmnLog;
import de.tudarmstadt.dvs.ukuflow.tools.exception.InvalidRepositoryNameException;
//import java.util.LinkedList;import java.util.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.io.InputStream;

/** Token Manager. */
public class ukuFlowScriptTokenManager implements ukuFlowScriptConstants
{

  /** Debug output. */
  public  java.io.PrintStream debugStream = System.out;
  /** Set debug output. */
  public  void setDebugStream(java.io.PrintStream ds) { debugStream = ds; }
private final int jjStopStringLiteralDfa_0(int pos, long active0)
{
   switch (pos)
   {
      case 0:
         if ((active0 & 0x6000L) != 0L)
         {
            jjmatchedKind = 20;
            return 45;
         }
         if ((active0 & 0x1000L) != 0L)
            return 17;
         return -1;
      case 1:
         if ((active0 & 0x6000L) != 0L)
         {
            jjmatchedKind = 20;
            jjmatchedPos = 1;
            return 45;
         }
         return -1;
      case 2:
         if ((active0 & 0x6000L) != 0L)
         {
            jjmatchedKind = 20;
            jjmatchedPos = 2;
            return 45;
         }
         return -1;
      case 3:
         if ((active0 & 0x6000L) != 0L)
         {
            jjmatchedKind = 20;
            jjmatchedPos = 3;
            return 45;
         }
         return -1;
      default :
         return -1;
   }
}
private final int jjStartNfa_0(int pos, long active0)
{
   return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
}
private int jjStopAtPos(int pos, int kind)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   return pos + 1;
}
private int jjMoveStringLiteralDfa0_0()
{
   switch(curChar)
   {
      case 33:
         return jjMoveStringLiteralDfa1_0(0x1000000L);
      case 37:
         return jjStopAtPos(0, 36);
      case 40:
         return jjStopAtPos(0, 32);
      case 41:
         return jjStopAtPos(0, 33);
      case 42:
         return jjStopAtPos(0, 34);
      case 47:
         return jjStopAtPos(0, 35);
      case 59:
         return jjStopAtPos(0, 31);
      case 60:
         jjmatchedKind = 26;
         return jjMoveStringLiteralDfa1_0(0x10000000L);
      case 61:
         jjmatchedKind = 30;
         return jjMoveStringLiteralDfa1_0(0x800000L);
      case 62:
         jjmatchedKind = 25;
         return jjMoveStringLiteralDfa1_0(0x8000000L);
      case 64:
         return jjStartNfaWithStates_0(0, 12, 17);
      case 76:
      case 108:
         return jjMoveStringLiteralDfa1_0(0x4000L);
      case 83:
      case 115:
         return jjMoveStringLiteralDfa1_0(0x2000L);
      default :
         return jjMoveNfa_0(2, 0);
   }
}
private int jjMoveStringLiteralDfa1_0(long active0)
{
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(0, active0);
      return 1;
   }
   switch(curChar)
   {
      case 61:
         if ((active0 & 0x800000L) != 0L)
            return jjStopAtPos(1, 23);
         else if ((active0 & 0x1000000L) != 0L)
            return jjStopAtPos(1, 24);
         else if ((active0 & 0x8000000L) != 0L)
            return jjStopAtPos(1, 27);
         else if ((active0 & 0x10000000L) != 0L)
            return jjStopAtPos(1, 28);
         break;
      case 67:
      case 99:
         return jjMoveStringLiteralDfa2_0(active0, 0x2000L);
      case 79:
      case 111:
         return jjMoveStringLiteralDfa2_0(active0, 0x4000L);
      default :
         break;
   }
   return jjStartNfa_0(0, active0);
}
private int jjMoveStringLiteralDfa2_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(0, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(1, active0);
      return 2;
   }
   switch(curChar)
   {
      case 67:
      case 99:
         return jjMoveStringLiteralDfa3_0(active0, 0x4000L);
      case 79:
      case 111:
         return jjMoveStringLiteralDfa3_0(active0, 0x2000L);
      default :
         break;
   }
   return jjStartNfa_0(1, active0);
}
private int jjMoveStringLiteralDfa3_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(1, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(2, active0);
      return 3;
   }
   switch(curChar)
   {
      case 65:
      case 97:
         return jjMoveStringLiteralDfa4_0(active0, 0x4000L);
      case 80:
      case 112:
         return jjMoveStringLiteralDfa4_0(active0, 0x2000L);
      default :
         break;
   }
   return jjStartNfa_0(2, active0);
}
private int jjMoveStringLiteralDfa4_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(2, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(3, active0);
      return 4;
   }
   switch(curChar)
   {
      case 69:
      case 101:
         if ((active0 & 0x2000L) != 0L)
            return jjStartNfaWithStates_0(4, 13, 45);
         break;
      case 76:
      case 108:
         if ((active0 & 0x4000L) != 0L)
            return jjStartNfaWithStates_0(4, 14, 45);
         break;
      default :
         break;
   }
   return jjStartNfa_0(3, active0);
}
private int jjStartNfaWithStates_0(int pos, int kind, int state)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) { return pos + 1; }
   return jjMoveNfa_0(state, pos + 1);
}
static final long[] jjbitVec0 = {
   0x1ff00000fffffffeL, 0xffffffffffffc000L, 0xffffffffL, 0x600000000000000L
};
static final long[] jjbitVec2 = {
   0x0L, 0x0L, 0x0L, 0xff7fffffff7fffffL
};
static final long[] jjbitVec3 = {
   0x3fffffffffL, 0x0L, 0x0L, 0x0L
};
static final long[] jjbitVec4 = {
   0x0L, 0xffffffffffffffffL, 0xffffffffffffffffL, 0xffffffffffffffffL
};
static final long[] jjbitVec5 = {
   0xffffffffffffffffL, 0xffffffffffffffffL, 0xffffL, 0x0L
};
static final long[] jjbitVec6 = {
   0xffffffffffffffffL, 0xffffffffffffffffL, 0x0L, 0x0L
};
static final long[] jjbitVec7 = {
   0x3fffffffffffL, 0x0L, 0x0L, 0x0L
};
private int jjMoveNfa_0(int startState, int curPos)
{
   int startsAt = 0;
   jjnewStateCnt = 45;
   int i = 1;
   jjstateSet[0] = startState;
   int kind = 0x7fffffff;
   for (;;)
   {
      if (++jjround == 0x7fffffff)
         ReInitRounds();
      if (curChar < 64)
      {
         long l = 1L << curChar;
         do
         {
            switch(jjstateSet[--i])
            {
               case 45:
                  if ((0x3ff001000000000L & l) != 0L)
                  {
                     if (kind > 29)
                        kind = 29;
                     jjCheckNAddStates(0, 2);
                  }
                  else if (curChar == 45)
                  {
                     if (kind > 29)
                        kind = 29;
                     jjCheckNAddTwoStates(28, 27);
                  }
                  if ((0x3ff001000000000L & l) != 0L)
                  {
                     if (kind > 20)
                        kind = 20;
                     jjCheckNAdd(44);
                  }
                  if (curChar == 36)
                  {
                     if (kind > 29)
                        kind = 29;
                     jjCheckNAddStates(0, 2);
                  }
                  break;
               case 2:
                  if ((0x3ff000000000000L & l) != 0L)
                  {
                     if (kind > 17)
                        kind = 17;
                     jjCheckNAddTwoStates(22, 23);
                  }
                  else if ((0x280000000000L & l) != 0L)
                  {
                     if (kind > 8)
                        kind = 8;
                  }
                  else if (curChar == 36)
                  {
                     if (kind > 20)
                        kind = 20;
                     jjCheckNAddStates(3, 6);
                  }
                  else if (curChar == 38)
                     jjstateSet[jjnewStateCnt++] = 4;
                  if (curChar == 45)
                  {
                     if (kind > 29)
                        kind = 29;
                     jjCheckNAddTwoStates(28, 27);
                  }
                  else if (curChar == 48)
                     jjstateSet[jjnewStateCnt++] = 25;
                  else if (curChar == 36)
                     jjstateSet[jjnewStateCnt++] = 20;
                  else if (curChar == 38)
                  {
                     if (kind > 5)
                        kind = 5;
                  }
                  break;
               case 3:
               case 4:
                  if (curChar == 38 && kind > 5)
                     kind = 5;
                  break;
               case 5:
                  if (curChar == 38)
                     jjstateSet[jjnewStateCnt++] = 4;
                  break;
               case 15:
                  if ((0x280000000000L & l) != 0L && kind > 8)
                     kind = 8;
                  break;
               case 17:
                  if (curChar != 36)
                     break;
                  if (kind > 15)
                     kind = 15;
                  jjCheckNAdd(18);
                  break;
               case 18:
                  if ((0x3ff001000000000L & l) == 0L)
                     break;
                  if (kind > 15)
                     kind = 15;
                  jjCheckNAdd(18);
                  break;
               case 19:
                  if (curChar == 36)
                     jjstateSet[jjnewStateCnt++] = 20;
                  break;
               case 20:
                  if (curChar != 36)
                     break;
                  if (kind > 16)
                     kind = 16;
                  jjCheckNAdd(21);
                  break;
               case 21:
                  if ((0x3ff001000000000L & l) == 0L)
                     break;
                  if (kind > 16)
                     kind = 16;
                  jjCheckNAdd(21);
                  break;
               case 22:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 17)
                     kind = 17;
                  jjCheckNAddTwoStates(22, 23);
                  break;
               case 24:
                  if (curChar == 48)
                     jjstateSet[jjnewStateCnt++] = 25;
                  break;
               case 26:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 17)
                     kind = 17;
                  jjCheckNAddTwoStates(26, 23);
                  break;
               case 27:
                  if (curChar != 45)
                     break;
                  if (kind > 29)
                     kind = 29;
                  jjCheckNAddTwoStates(28, 27);
                  break;
               case 28:
                  if (curChar != 36)
                     break;
                  if (kind > 29)
                     kind = 29;
                  jjCheckNAddStates(0, 2);
                  break;
               case 29:
                  if ((0x3ff001000000000L & l) == 0L)
                     break;
                  if (kind > 29)
                     kind = 29;
                  jjCheckNAddStates(0, 2);
                  break;
               case 43:
                  if (curChar != 36)
                     break;
                  if (kind > 20)
                     kind = 20;
                  jjCheckNAddStates(3, 6);
                  break;
               case 44:
                  if ((0x3ff001000000000L & l) == 0L)
                     break;
                  if (kind > 20)
                     kind = 20;
                  jjCheckNAdd(44);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else if (curChar < 128)
      {
         long l = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 45:
                  if ((0x7fffffe87fffffeL & l) != 0L)
                  {
                     if (kind > 29)
                        kind = 29;
                     jjCheckNAddStates(0, 2);
                  }
                  if ((0x7fffffe87fffffeL & l) != 0L)
                  {
                     if (kind > 29)
                        kind = 29;
                     jjCheckNAddStates(0, 2);
                  }
                  if ((0x7fffffe87fffffeL & l) != 0L)
                  {
                     if (kind > 20)
                        kind = 20;
                     jjCheckNAdd(44);
                  }
                  if (curChar == 95)
                  {
                     if (kind > 29)
                        kind = 29;
                     jjCheckNAddTwoStates(28, 27);
                  }
                  break;
               case 2:
                  if ((0x7fffffe87fffffeL & l) != 0L)
                  {
                     if (kind > 20)
                        kind = 20;
                     jjCheckNAddStates(3, 6);
                  }
                  else if (curChar == 64)
                     jjstateSet[jjnewStateCnt++] = 17;
                  else if (curChar == 126)
                  {
                     if (kind > 7)
                        kind = 7;
                  }
                  else if (curChar == 124)
                     jjstateSet[jjnewStateCnt++] = 9;
                  if ((0x4000000040L & l) != 0L)
                     jjAddStates(7, 8);
                  else if ((0x10000000100000L & l) != 0L)
                     jjAddStates(9, 10);
                  else if ((0x400000004000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 12;
                  else if ((0x800000008000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 6;
                  else if ((0x200000002L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 1;
                  else if (curChar == 95)
                  {
                     if (kind > 29)
                        kind = 29;
                     jjCheckNAddTwoStates(28, 27);
                  }
                  else if (curChar == 124)
                  {
                     if (kind > 6)
                        kind = 6;
                  }
                  break;
               case 0:
                  if ((0x1000000010L & l) != 0L && kind > 5)
                     kind = 5;
                  break;
               case 1:
                  if ((0x400000004000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 0;
                  break;
               case 6:
                  if ((0x4000000040000L & l) != 0L && kind > 6)
                     kind = 6;
                  break;
               case 7:
                  if ((0x800000008000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 6;
                  break;
               case 8:
               case 9:
                  if (curChar == 124 && kind > 6)
                     kind = 6;
                  break;
               case 10:
                  if (curChar == 124)
                     jjstateSet[jjnewStateCnt++] = 9;
                  break;
               case 11:
                  if ((0x10000000100000L & l) != 0L && kind > 7)
                     kind = 7;
                  break;
               case 12:
                  if ((0x800000008000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 11;
                  break;
               case 13:
                  if ((0x400000004000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 12;
                  break;
               case 14:
                  if (curChar == 126)
                     kind = 7;
                  break;
               case 16:
                  if (curChar == 64)
                     jjstateSet[jjnewStateCnt++] = 17;
                  break;
               case 17:
               case 18:
                  if ((0x7fffffe87fffffeL & l) == 0L)
                     break;
                  if (kind > 15)
                     kind = 15;
                  jjCheckNAdd(18);
                  break;
               case 20:
               case 21:
                  if ((0x7fffffe87fffffeL & l) == 0L)
                     break;
                  if (kind > 16)
                     kind = 16;
                  jjCheckNAdd(21);
                  break;
               case 23:
                  if ((0x100000001000L & l) != 0L && kind > 17)
                     kind = 17;
                  break;
               case 25:
                  if ((0x100000001000000L & l) != 0L)
                     jjCheckNAdd(26);
                  break;
               case 26:
                  if ((0x7e0000007eL & l) == 0L)
                     break;
                  if (kind > 17)
                     kind = 17;
                  jjCheckNAddTwoStates(26, 23);
                  break;
               case 27:
                  if (curChar != 95)
                     break;
                  if (kind > 29)
                     kind = 29;
                  jjCheckNAddTwoStates(28, 27);
                  break;
               case 28:
                  if ((0x7fffffe87fffffeL & l) == 0L)
                     break;
                  if (kind > 29)
                     kind = 29;
                  jjCheckNAddStates(0, 2);
                  break;
               case 29:
                  if ((0x7fffffe87fffffeL & l) == 0L)
                     break;
                  if (kind > 29)
                     kind = 29;
                  jjCheckNAddStates(0, 2);
                  break;
               case 30:
                  if ((0x10000000100000L & l) != 0L)
                     jjAddStates(9, 10);
                  break;
               case 31:
                  if ((0x2000000020L & l) != 0L && kind > 9)
                     kind = 9;
                  break;
               case 32:
               case 34:
                  if ((0x20000000200000L & l) != 0L)
                     jjCheckNAdd(31);
                  break;
               case 33:
                  if ((0x4000000040000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 32;
                  break;
               case 35:
                  if ((0x4000000040000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 34;
                  break;
               case 36:
                  if ((0x4000000040L & l) != 0L)
                     jjAddStates(7, 8);
                  break;
               case 37:
               case 40:
                  if ((0x8000000080000L & l) != 0L)
                     jjCheckNAdd(31);
                  break;
               case 38:
                  if ((0x100000001000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 37;
                  break;
               case 39:
                  if ((0x200000002L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 38;
                  break;
               case 41:
                  if ((0x100000001000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 40;
                  break;
               case 42:
                  if ((0x200000002L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 41;
                  break;
               case 43:
                  if ((0x7fffffe87fffffeL & l) == 0L)
                     break;
                  if (kind > 20)
                     kind = 20;
                  jjCheckNAddStates(3, 6);
                  break;
               case 44:
                  if ((0x7fffffe87fffffeL & l) == 0L)
                     break;
                  if (kind > 20)
                     kind = 20;
                  jjCheckNAdd(44);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int hiByte = (int)(curChar >> 8);
         int i1 = hiByte >> 6;
         long l1 = 1L << (hiByte & 077);
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 45:
                  if (jjCanMove_0(hiByte, i1, i2, l1, l2))
                  {
                     if (kind > 20)
                        kind = 20;
                     jjCheckNAdd(44);
                  }
                  if (jjCanMove_0(hiByte, i1, i2, l1, l2))
                  {
                     if (kind > 29)
                        kind = 29;
                     jjCheckNAddStates(0, 2);
                  }
                  if (jjCanMove_0(hiByte, i1, i2, l1, l2))
                  {
                     if (kind > 29)
                        kind = 29;
                     jjCheckNAddStates(0, 2);
                  }
                  break;
               case 2:
                  if (!jjCanMove_0(hiByte, i1, i2, l1, l2))
                     break;
                  if (kind > 20)
                     kind = 20;
                  jjCheckNAddStates(3, 6);
                  break;
               case 17:
               case 18:
                  if (!jjCanMove_0(hiByte, i1, i2, l1, l2))
                     break;
                  if (kind > 15)
                     kind = 15;
                  jjCheckNAdd(18);
                  break;
               case 20:
               case 21:
                  if (!jjCanMove_0(hiByte, i1, i2, l1, l2))
                     break;
                  if (kind > 16)
                     kind = 16;
                  jjCheckNAdd(21);
                  break;
               case 28:
                  if (!jjCanMove_0(hiByte, i1, i2, l1, l2))
                     break;
                  if (kind > 29)
                     kind = 29;
                  jjCheckNAddStates(0, 2);
                  break;
               case 29:
                  if (!jjCanMove_0(hiByte, i1, i2, l1, l2))
                     break;
                  if (kind > 29)
                     kind = 29;
                  jjCheckNAddStates(0, 2);
                  break;
               case 44:
                  if (!jjCanMove_0(hiByte, i1, i2, l1, l2))
                     break;
                  if (kind > 20)
                     kind = 20;
                  jjCheckNAdd(44);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      if (kind != 0x7fffffff)
      {
         jjmatchedKind = kind;
         jjmatchedPos = curPos;
         kind = 0x7fffffff;
      }
      ++curPos;
      if ((i = jjnewStateCnt) == (startsAt = 45 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { return curPos; }
   }
}
static final int[] jjnextStates = {
   28, 29, 27, 44, 28, 29, 27, 39, 42, 33, 35, 
};
private static final boolean jjCanMove_0(int hiByte, int i1, int i2, long l1, long l2)
{
   switch(hiByte)
   {
      case 0:
         return ((jjbitVec2[i2] & l2) != 0L);
      case 45:
         return ((jjbitVec3[i2] & l2) != 0L);
      case 48:
         return ((jjbitVec4[i2] & l2) != 0L);
      case 49:
         return ((jjbitVec5[i2] & l2) != 0L);
      case 51:
         return ((jjbitVec6[i2] & l2) != 0L);
      case 61:
         return ((jjbitVec7[i2] & l2) != 0L);
      default :
         if ((jjbitVec0[i1] & l1) != 0L)
            return true;
         return false;
   }
}

/** Token literal values. */
public static final String[] jjstrLiteralImages = {
"", null, null, null, null, null, null, null, null, null, null, null, "\100", 
null, null, null, null, null, null, null, null, null, null, "\75\75", "\41\75", 
"\76", "\74", "\76\75", "\74\75", null, "\75", "\73", "\50", "\51", "\52", "\57", 
"\45", };

/** Lexer state names. */
public static final String[] lexStateNames = {
   "DEFAULT",
};
static final long[] jjtoToken = {
   0x1fff93f3e1L, 
};
static final long[] jjtoSkip = {
   0x1eL, 
};
protected SimpleCharStream input_stream;
private final int[] jjrounds = new int[45];
private final int[] jjstateSet = new int[90];
protected char curChar;
/** Constructor. */
public ukuFlowScriptTokenManager(SimpleCharStream stream){
   if (SimpleCharStream.staticFlag)
      throw new Error("ERROR: Cannot use a static CharStream class with a non-static lexical analyzer.");
   input_stream = stream;
}

/** Constructor. */
public ukuFlowScriptTokenManager(SimpleCharStream stream, int lexState){
   this(stream);
   SwitchTo(lexState);
}

/** Reinitialise parser. */
public void ReInit(SimpleCharStream stream)
{
   jjmatchedPos = jjnewStateCnt = 0;
   curLexState = defaultLexState;
   input_stream = stream;
   ReInitRounds();
}
private void ReInitRounds()
{
   int i;
   jjround = 0x80000001;
   for (i = 45; i-- > 0;)
      jjrounds[i] = 0x80000000;
}

/** Reinitialise parser. */
public void ReInit(SimpleCharStream stream, int lexState)
{
   ReInit(stream);
   SwitchTo(lexState);
}

/** Switch to specified lex state. */
public void SwitchTo(int lexState)
{
   if (lexState >= 1 || lexState < 0)
      throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", TokenMgrError.INVALID_LEXICAL_STATE);
   else
      curLexState = lexState;
}

protected Token jjFillToken()
{
   final Token t;
   final String curTokenImage;
   final int beginLine;
   final int endLine;
   final int beginColumn;
   final int endColumn;
   String im = jjstrLiteralImages[jjmatchedKind];
   curTokenImage = (im == null) ? input_stream.GetImage() : im;
   beginLine = input_stream.getBeginLine();
   beginColumn = input_stream.getBeginColumn();
   endLine = input_stream.getEndLine();
   endColumn = input_stream.getEndColumn();
   t = Token.newToken(jjmatchedKind, curTokenImage);

   t.beginLine = beginLine;
   t.endLine = endLine;
   t.beginColumn = beginColumn;
   t.endColumn = endColumn;

   return t;
}

int curLexState = 0;
int defaultLexState = 0;
int jjnewStateCnt;
int jjround;
int jjmatchedPos;
int jjmatchedKind;

/** Get the next Token. */
public Token getNextToken() 
{
  Token matchedToken;
  int curPos = 0;

  EOFLoop :
  for (;;)
  {
   try
   {
      curChar = input_stream.BeginToken();
   }
   catch(java.io.IOException e)
   {
      jjmatchedKind = 0;
      matchedToken = jjFillToken();
      return matchedToken;
   }

   try { input_stream.backup(0);
      while (curChar <= 32 && (0x100002600L & (1L << curChar)) != 0L)
         curChar = input_stream.BeginToken();
   }
   catch (java.io.IOException e1) { continue EOFLoop; }
   jjmatchedKind = 0x7fffffff;
   jjmatchedPos = 0;
   curPos = jjMoveStringLiteralDfa0_0();
   if (jjmatchedKind != 0x7fffffff)
   {
      if (jjmatchedPos + 1 < curPos)
         input_stream.backup(curPos - jjmatchedPos - 1);
      if ((jjtoToken[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
      {
         matchedToken = jjFillToken();
         return matchedToken;
      }
      else
      {
         continue EOFLoop;
      }
   }
   int error_line = input_stream.getEndLine();
   int error_column = input_stream.getEndColumn();
   String error_after = null;
   boolean EOFSeen = false;
   try { input_stream.readChar(); input_stream.backup(1); }
   catch (java.io.IOException e1) {
      EOFSeen = true;
      error_after = curPos <= 1 ? "" : input_stream.GetImage();
      if (curChar == '\n' || curChar == '\r') {
         error_line++;
         error_column = 0;
      }
      else
         error_column++;
   }
   if (!EOFSeen) {
      input_stream.backup(1);
      error_after = curPos <= 1 ? "" : input_stream.GetImage();
   }
   throw new TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, TokenMgrError.LEXICAL_ERROR);
  }
}

private void jjCheckNAdd(int state)
{
   if (jjrounds[state] != jjround)
   {
      jjstateSet[jjnewStateCnt++] = state;
      jjrounds[state] = jjround;
   }
}
private void jjAddStates(int start, int end)
{
   do {
      jjstateSet[jjnewStateCnt++] = jjnextStates[start];
   } while (start++ != end);
}
private void jjCheckNAddTwoStates(int state1, int state2)
{
   jjCheckNAdd(state1);
   jjCheckNAdd(state2);
}

private void jjCheckNAddStates(int start, int end)
{
   do {
      jjCheckNAdd(jjnextStates[start]);
   } while (start++ != end);
}

}
