package bls

import spinal.core._


// Hardware definition
case class BLS() extends Component {

  val N = 384
  val MOD_PRIME = 107

  val io = new Bundle {
    val clk = in Bool()
    val rst = in Bool()
    val a = in UInt(N bits)
    val b = in UInt(N bits)
    val m = in UInt(N bits)
    val m_prime = in UInt(N bits)
    val result = out UInt(N bits)
  }


}

object MyTopLevelVerilog extends App {
  Config.spinal.generateVerilog(BLS())
}

object MyTopLevelVhdl extends App {
  Config.spinal.generateVhdl(BLS())
}
