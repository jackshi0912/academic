import Test.Hspec
import Calculator
import Data.Maybe

main :: IO ()
main = hspec $ do

  describe "The expression language: " $ do
    describe "interp Expr test:" $ do
      it "Testing: addition subtraction" $ do
        interp (minus (plus (Lit 2) (Lit 2)) (Lit 3)) `shouldBe` 1
      it "Testing: addition multiplication" $ do
        interp (times (plus (Lit 2) (Lit 2)) (Lit 3)) `shouldBe` 12
      it "Testing: zero multiplication" $ do
        interp (times (minus (Lit 2) (Lit 2)) (Lit 3)) `shouldBe` 0

  describe "A simple optimizer: " $ do
    describe "simplifyZero Expr test" $ do
      it "Testing: right 0 simplify" $ do
        simplifyZero (plus (plus (Lit 2) (Lit 2)) (Lit 0)) `shouldBe`
          plus (Lit 2) (Lit 2)
        simplifyZero (plus (Lit 1) (Lit 0)) `shouldBe` Lit 1
      it "Testing: left 0 simplify" $ do
        simplifyZero (plus (Lit 0) (Lit 1)) `shouldBe` Lit 1
      it "Testing: double 0 simplify" $ do
        simplifyZero (plus (Lit 0) (Lit 0)) `shouldBe` Lit 0
      it "Testing: sub-expression skipping 0 simplify" $ do
        simplifyZero (plus (plus (Lit 2) (Lit 0)) (Lit 0)) `shouldBe`
          plus (Lit 2) (Lit 0)

    describe "peephole simplifyZero Expr test" $ do
      it "Testing: double sub-expression simplify" $ do
        peephole simplifyZero (plus (plus (Lit 2) (Lit 0)) (plus (Lit 1) (Lit 0)))
          `shouldBe` plus (Lit 2) (Lit 1)
      it "Testing: double sub-expression and top level simplify" $ do
        peephole simplifyZero (plus (plus (Lit 2) (Lit 0)) (plus (Lit 0) (Lit 0)))
          `shouldBe` Lit 2
        peephole simplifyZero (plus (plus (Lit 2) (Lit 0)) (Lit 0)) `shouldBe`
          Lit 2
      it "Testing: nested-expression" $ do
        peephole simplifyZero (plus (Lit 0) (plus (plus (Lit 0)
          (plus (Lit 0) (Lit 0))) (Lit 0))) `shouldBe` Lit 0

  describe "The instruction language: " $ do
    describe "step test" $ do
      it "Testing: Push numbers" $ do
        fromJust (step (IPush 0) []) `shouldBe` [0]
        fromJust (step (IPush 2) (fromJust (step (IPush 1)
          (fromJust (step (IPush 0) []))))) `shouldBe` [2, 1, 0]
      it "Testing: Pushing then adding legally" $ do
        fromJust (step (IOp Plus) (fromJust (step (IPush 2)
          (fromJust (step (IPush 1) []))))) `shouldBe` [3]
      it "Testing: Adding illegally" $ do
        step (IOp Plus) [] `shouldBe` Nothing
        step (IOp Plus) (fromJust (step (IPush 1) [])) `shouldBe` Nothing

    describe "run test" $ do
      it "Testing: run addition sequence" $ do
        fromJust (run [(IPush 0), (IPush 1), (IOp Plus)] []) `shouldBe` [1]
      it "Testing: run addition sequence with prior data" $ do
        fromJust (run [(IPush 1), (IOp Plus)] [1]) `shouldBe` [2]
      it "Testing: run illegal sequence" $ do
        run [(IPush 1), (IOp Plus)] [] `shouldBe` Nothing

  describe "A simple compiler: " $ do
    describe "compile test" $ do
      it "Testing: minus order" $ do
        compile (times (minus (Lit 1) (Lit 2)) (Lit 3)) `shouldBe`
          [IPush 3, IPush 2, IPush 1, IOp Minus, IOp Times]
        fromJust (run (compile (times (minus (Lit 1) (Lit 2)) (Lit 3))) [])
          `shouldBe` [-3]
      it "Testing: compile nested expression" $ do
        compile (times (plus (Lit 1) (Lit 2)) (Lit 3)) `shouldBe`
          [IPush 3, IPush 2, IPush 1, IOp Plus, IOp Times]
        fromJust (run (compile (times (plus (Lit 1) (Lit 2)) (Lit 3))) [])
          `shouldBe` [9]
      it "Testing: double nested expression" $ do
        fromJust (run (compile (plus (minus (times (minus (Lit (-3)) (Lit (-4)))
          (Lit 0)) (Lit 4)) (Lit (-7)))) []) `shouldBe` [-11]
      it "Testing: literal left expression" $ do
        fromJust (run (compile (times (Lit (-1)) (minus (Lit 1) (Lit 0)))) [])
          `shouldBe` [-1]
      it "Testing: literal right expression" $ do
        fromJust (run (compile (times (minus (Lit 1) (Lit 0)) (Lit (-1)))) [])
          `shouldBe` [-1]

    describe "decompile consistency test" $ do
      it "Testing: decompile nested expression" $ do
        fromJust (decompile (compile (minus (Lit 0) (plus (Lit 0) (Lit 0)))))
          `shouldBe` minus (Lit 0) (plus (Lit 0) (Lit 0))