package bls

import bls.dataTypes.JacobianPoint
import spinal.core._
import spinal.lib.{master, slave}


// Hardware definition
case class BLS(N: Int = 6, MOD_PRIME: Int = 23) extends Component {

  val io = new Bundle {
    val a = slave Stream JacobianPoint(N)
    val b = slave Stream JacobianPoint(N)
    val c = master Stream JacobianPoint(N)
  }

  val pointAdder = new JacobianPointAdder(N = N, MOD_PRIME = MOD_PRIME)

  pointAdder.io <> io

}

object MyTopLevelVerilog extends App {
  Config.spinal.generateVerilog(BLS())
}

