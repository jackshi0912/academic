module Main where
import Warmup

main = do
{-
  print (digitsOfInt 3124)
  print (removeZero [0, 1, 0, 0, 0, 2, 0, 3, 4, 0, 1])
  print (removeZero [0])
  print (removeZero [0, 1])
  print (removeZero [1, 0])
  print (stringOfList intString [1,2,3,4,5,6])
  print (bigAdd [9, 9, 9, 9, 9] [9, 9, 9, 9, 9])
  print (bigAdd [9, 9] [1, 0, 0, 2])
  print (mulByDigit 9 [9, 9, 9, 9])
-}
  print(bigMul [9,9,9,9] [9,9,9,9])  -- [9,9,9,8,0,0,0,1]
  print(bigMul [9,9,9,9,9] [9,9,9,9,9]) -- [9,9,9,9,8,0,0,0,0,1]
