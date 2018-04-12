module Monads where

import Data.Map (Map)
import qualified Data.Map as Map
import Control.Monad.State hiding ((>=>))
import Control.Applicative
import Control.Monad hiding ((>=>))

-----------------------------------------------------------------------

--                          Maybe monad

-----------------------------------------------------------------------

-- Recall Calculator.hs.  In it, you wrote a step function to interpret
-- instructions, and a run function which executed instructions
-- one-by-one until there were none left.

type Stack = [Int]
data Instr = IPlus
           | IPush Int

step :: Instr -> Stack -> Maybe Stack
step IPlus (x:y:rest) = Just (x + y:rest)
step (IPush n) rest = Just (n:rest)
step _ _ = Nothing

run0 :: [Instr] -> Stack -> Maybe Stack
run0 (i:is) stk =
    case step i stk of
        Just stk' -> run is stk'
        Nothing -> Nothing
run0 [] stk = Just stk

-- Maybe is a monad: a Maybe action either fails and short-circuits
-- (with Nothing) or succeeds and continues.  Rewrite 'run' using monads
-- (using either do notation or bind).  Your solution should NOT use
-- case, Nothing or Just.

run :: [Instr] -> Stack -> Maybe Stack
-- BEGIN run (DO NOT DELETE THIS LINE)
run = undefined
-- END run (DO NOT DELETE THIS LINE)

-----------------------------------------------------------------------

--                          State monad

-----------------------------------------------------------------------

-- Consider the following simple lambda calculus program. In it, we manually
-- maintain a global counter that we use to supply "fresh" variables to our
-- substitution function.

data Expr = Var Name
          | Lambda Name Expr
          | App Expr Expr
    deriving (Eq)
type Name = String
type Subst = Map Name Expr
type FreshSupply = Int

freshDumb0 :: FreshSupply -> (Name, FreshSupply)
freshDumb0 u = ("$u" ++ show u, u+1)

substDumb0 :: FreshSupply -> Expr -> Subst -> (Expr, FreshSupply)
substDumb0 u (Var x) s =
    case Map.lookup x s of
        Just e  -> (e, u)
        Nothing -> (Var x, u)
substDumb0 u (App e1 e2) s =
    let (e1', u') = substDumb0 u e1 s
        (e2', u'') = substDumb0 u' e2 s
    in (App e1' e2', u'')
substDumb0 u (Lambda x e) s =
    let (y, u') = freshDumb0 u
        (e', u'') = substDumb0 u' e (Map.insert x (Var y) s)
    in (Lambda y e', u'')

-- This pattern can be abstracted into a "state" monad, which handles
-- threading the state through calls.

type UniqMonad0 a = FreshSupply -> (a, FreshSupply)

-- To make it a monad, we have to define two operations.
-- (We've also given you versions of the types with the
-- type synonym expanded.)

--  returnU :: a -> (FreshSupply -> (a, FreshSupply))
returnU :: a -> UniqMonad0 a
-- BEGIN returnU (DO NOT DELETE THIS LINE)
returnU = undefined
-- END returnU (DO NOT DELETE THIS LINE)

--  bindU :: (FreshSupply -> (a, FreshSupply))
--        -> (a -> (FreshSupply -> (b, FreshSupply)))
--        -> (FreshSupply -> (b, FreshSupply))
bindU :: UniqMonad0 a -> (a -> UniqMonad0 b) -> UniqMonad0 b
-- BEGIN bindU (DO NOT DELETE THIS LINE)
bindU = undefined
-- END bindU (DO NOT DELETE THIS LINE)

-- Unfortunately, to actually define a monad instance, we can't
-- use the type 'UniqMonad0' directly; we have to define a new
-- data type to represent the monad.  Here, we've gone ahead and
-- written the necessary plumbing to make this work.

data UniqMonad a
    = UniqMonad { runUniqMonad :: FreshSupply -> (a, FreshSupply) }

-- In GHC 7.10, we also have to define Functor and Applicative

instance Functor UniqMonad where
    fmap = liftM

instance Applicative UniqMonad where
    pure  = return
    (<*>) = ap

instance Monad UniqMonad where
    return x = UniqMonad $ returnU x
    m >>= f = UniqMonad $ bindU (runUniqMonad m) (runUniqMonad . f)

freshDumb :: UniqMonad Name
freshDumb = UniqMonad $ \u -> ("$u" ++ show u, u+1)

-- Rewrite substDumb using monads.  Specifically, you should not have
-- any variable in scope which has type FreshSupply.

substDumb :: Expr -> Subst -> UniqMonad Expr
-- BEGIN substDumb (DO NOT DELETE THIS LINE)
substDumb = undefined
-- END substDumb (DO NOT DELETE THIS LINE)

-----------------------------------------------------------------------

--                          Combinators

-----------------------------------------------------------------------

-- In lecture, we mentioned the monad laws:
--
--  return x >>= f                  === f x
--  m >>= return                    === m
--  m >>= (\x -> m2 >>= \y -> m3)   === (m1 >>= (\x -> m2)) >>= \y -> m3
--
-- You might think that these laws are weirdly asymmetric, and they
-- are!  Another way of expressing the monad laws is in terms of the
-- monad composition operator >=>.  This operator has the following
-- properties:
--
--  return >=> f        === f
--  f >=> return        === f
--  (f >=> g) >=> h     === f >=> (g >=> h)
--
-- Define (>=>) in terms of (>>=):

(>=>) :: Monad m => (a -> m b) -> (b -> m c) -> (a -> m c)
-- BEGIN (>=>) (DO NOT DELETE THIS LINE)
(>=>) = undefined
-- END (>=>) (DO NOT DELETE THIS LINE)

-- In fact, (>=>) is equivalent in expressivity to (>>=).  To
-- show this, define (>>=) using ONLY (>=>).

bind :: Monad m => m a -> (a -> m b) -> m b
-- BEGIN bind (DO NOT DELETE THIS LINE)
bind = undefined
-- END bind (DO NOT DELETE THIS LINE)
