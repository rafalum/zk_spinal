package bls.dataTypes

import spinal.core._

case class JacobianPoint(N: Int = 384) extends Bundle with Data {
  val x : UInt = UInt(N bits)
  val y : UInt = UInt(N bits)
  val z : UInt = UInt(N bits)
}
