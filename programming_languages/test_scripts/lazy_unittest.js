var assert = require('assert');
var equals = require('array-equal');
var lazy = require('../JS/lazy');

describe("Lazy Evaluation Test", () => {
  describe("Unit Test", () => {

    it("test delay", () => {
      let f = (() => {
        let counter = 0;
        return () => {
          return counter++;
        }
      }) ();
      let thunk = lazy.delay(f);
      let result1 = lazy.force(thunk);
      let result2 = lazy.force(thunk);
      assert(result1 === result2)
    });

    it("test repeat", () => {
      assert(equals([2, 2, 2, 2, 2], lazy.take(5, lazy.repeat(2))))
    });


    it("test enumForm", () => {
      assert(equals([2, 3, 4, 5, 6], lazy.take(5, lazy.enumFrom(2))))
    });

    it("test map", () => {
      assert(equals([-2, -3, -4, -5, -6],
          lazy.take(5, lazy.map(
              x=>-x,lazy.enumFrom(2)))))
    });

    it("test zipWith", () => {
      assert(equals([true, true, true, false, false],
          lazy.take(5, lazy.zipWith((x, y)=> x<=y,
            lazy.enumFrom(2),
            lazy.repeat(4)))))
    });

    it("test tail", () => {
      assert(equals([3, 4, 5, 6, 7], lazy.take(5, lazy.tail(lazy.enumFrom(2)))))
    });

    it("test cons", () => {
      assert(equals([1, 2, 3, 4, 5],
          lazy.take(5, lazy.cons(1, lazy.enumFrom(2)))))
    });

  });
});