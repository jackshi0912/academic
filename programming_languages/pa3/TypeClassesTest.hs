import Test.Hspec
import TypeClasses

main :: IO ()
main = hspec $ do

  describe "Int sort test:" $ do
    describe "Ascending:" $ do
      it "sort unordered list" $ do
        sort dOrdInt [2, 3, 1] `shouldBe` [1, 2, 3]
        sort dOrdInt [3, 2, 1, 0] `shouldBe` [0, 1, 2, 3]
      it "sort singleton list" $ do
        sort dOrdInt [1] `shouldBe` [1]
      it "sort empty list" $ do
        sort dOrdInt [] `shouldBe` []

    describe "Descending:" $ do
      it "sort unordered list" $ do
        sortIntDesc [2, 3, 1] `shouldBe` [3, 2, 1]
        sortIntDesc [0, 1, 2, 3] `shouldBe` [3, 2, 1, 0]
      it "sort singleton list" $ do
        sortIntDesc [1] `shouldBe` [1]
      it "sort empty list" $ do
        sortIntDesc [] `shouldBe` []

    describe "Descending with parametric dictionary:" $ do
      it "sort unordered list" $ do
        sortIntDesc' dOrdIntDesc [2, 3, 1] `shouldBe` [3, 2, 1]
        sortIntDesc' dOrdIntDesc [0, 1, 2, 3] `shouldBe` [3, 2, 1, 0]
      it "sort singleton list" $ do
        sortIntDesc' dOrdIntDesc [1] `shouldBe` [1]
      it "sort empty list" $ do
        sortIntDesc' dOrdIntDesc [] `shouldBe` []

  describe "Tuple sort test:" $ do
    describe "Sort X first then Y:" $ do
      it "sort unordered tuples" $ do
        sort (dOrdXY dOrdInt dOrdInt) [(2, 4), (3, 5), (1, 6), (0, 7)]
          `shouldBe` [(0, 7), (1, 6), (2, 4), (3, 5)]
      it "sort singleton list" $ do
        sort (dOrdXY dOrdInt dOrdInt) [(0, 1)] `shouldBe` [(0, 1)]
      it "sort empty list" $ do
        sort (dOrdXY dOrdInt dOrdInt) [] `shouldBe` []

    describe "Sort Y first then x:" $ do
      it "sort unordered tuples" $ do
        sort (dOrdYX dOrdInt dOrdInt) [(4, 2), (5, 3), (6, 1), (7, 0)]
          `shouldBe` [(7, 0), (6, 1), (4, 2), (5, 3)]
      it "sort singleton list" $ do
        sort (dOrdYX dOrdInt dOrdInt) [(0, 1)] `shouldBe` [(0, 1)]
      it "sort empty list" $ do
        sort (dOrdYX dOrdInt dOrdInt) [] `shouldBe` []

  describe "Test if list is sorted:" $ do
    describe "Integer list test:" $ do
      it "empty list" $ do
        isSorted dOrdInt [] `shouldBe` True
      it "singleton list" $ do
        isSorted dOrdInt [1] `shouldBe` True
      it "sorted list" $ do
        isSorted dOrdInt [1, 2, 3, 4, 5] `shouldBe` True
      it "unsorted list" $ do
        isSorted dOrdInt [5, 4, 3, 2, 1] `shouldBe` False

    describe "Tuple list test:" $ do
      it "empty list" $ do
        isSorted (dOrdXY dOrdInt dOrdInt) [] `shouldBe` True
      it "singleton list" $ do
        isSorted (dOrdXY dOrdInt dOrdInt) [(2, 1)] `shouldBe` True
      it "sorted list" $ do
        isSorted (dOrdXY dOrdInt dOrdInt)
          [(1, 5), (2, 4), (3, 3), (3, 4), (5, 1)] `shouldBe` True
      it "unsorted list" $ do
        isSorted (dOrdXY dOrdInt dOrdInt)
          [(3, 5), (1, 4), (2, 3), (3, 4), (5, 1)] `shouldBe` False
