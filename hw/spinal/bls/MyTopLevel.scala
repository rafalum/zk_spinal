package projectname

import spinal.core._

// Hardware definition
case class MyTopLevel() extends Component {

  val N = 32

  val io = new Bundle {
    val clk = in Bool()
    val rst = in Bool()
    val start = in Bool()
    val a = in Bits(N bits)
    val b = in Bits(N bits)
    val m = in Bits(N bits)
    val m_prime = in Bits(N bits)
    val result = out Bits(N bits)
    val done = out Bool()
  }


  val montgomeryMultiplier = new MontgomeryMultiplier(N)

  // Connect the BlackBox to the rest of your design here
  montgomeryMultiplier.io.clk := io.clk
  montgomeryMultiplier.io.rst := io.rst
  montgomeryMultiplier.io.start := io.start
  montgomeryMultiplier.io.a := io.a
  montgomeryMultiplier.io.b := io.b
  montgomeryMultiplier.io.m := io.m
  montgomeryMultiplier.io.m_prime := io.m_prime

  io.result := montgomeryMultiplier.io.result
  io.done := montgomeryMultiplier.io.done
}

object MyTopLevelVerilog extends App {
  Config.spinal.generateVerilog(MyTopLevel())
}

object MyTopLevelVhdl extends App {
  Config.spinal.generateVhdl(MyTopLevel())
}
