package com.enshahar.turtle.unit

import org.scalatest._

import DoubleOps._

class TestAngleUnit extends FunSpec {
  describe("DEGREE") {
    val one = DEGREE
    it("converts to RADIAN") { assert(one.toRadian <==> 1.0 / 360.0 * 2.0 * Math.PI) }
    it("converts to DEGREE") { assert(one.toDegree <==> 1.0 ) }
    it("converts to MINUTE") { assert(one.toMinute <==> 60.0 ) }
    it("converts to SECOND") { assert(one.toSecond <==> 3600.0 ) }
  }

  describe("RADIAN") {
    val one = RADIAN
    it("converts to RADIAN") { assert(one.toRadian <==> 1.0) }
    it("converts to DEGREE") { assert(one.toDegree <==> 1.0 / (2.0 * Math.PI) * 360.0 ) }
    it("converts to MINUTE") { assert(one.toMinute <==> 1.0 / (2.0 * Math.PI) * 360.0 * 60.0 ) }
    it("converts to SECOND") { assert(one.toSecond <==> 1.0 / (2.0 * Math.PI) * 360.0 * 3600.0 ) }
  }

  describe("MINUTE") {
    val one = MINUTE
    it("converts to RADIAN") { assert(one.toRadian <==> 1.0 / 360.0 * 2.0 * Math.PI / 60.0 ) }
    it("converts to DEGREE") { assert(one.toDegree <==> 1.0 / 60.0 ) }
    it("converts to MINUTE") { assert(one.toMinute <==> 1.0 ) }
    it("converts to SECOND") { assert(one.toSecond <==> 1.0 * 60.0 ) }
  }

  describe("SECONDS") {
    val one = SECOND
    it("converts to RADIAN") { assert(one.toRadian <==> 1.0 / 360.0 * 2.0 * Math.PI / 60.0 / 60.0 ) }
    it("converts to DEGREE") { assert(one.toDegree <==> 1.0 / 60.0 / 60.0 ) }
    it("converts to MINUTE") { assert(one.toMinute <==> 1.0 / 60.0 ) }
    it("converts to SECOND") { assert(one.toSecond <==> 1.0 ) }
  }
}
