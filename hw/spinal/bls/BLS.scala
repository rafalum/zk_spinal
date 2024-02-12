package bls

import spinal.core._


// Hardware definition
case class BLS(N: Int = 8, MOD_PRIME: Int = 107) extends Component {


  val io = new Bundle {
    val a = in UInt(N bits)
    val b = in UInt(N bits)
    val result = out UInt(N bits)
  }

  val simpleModuloMultiplier = new SimpleModuloMultiplier(N = N, MOD_PRIME = MOD_PRIME)

  simpleModuloMultiplier.io.a := io.a
  simpleModuloMultiplier.io.b := io.b

  io.result := simpleModuloMultiplier.io.o

}

object MyTopLevelVerilog extends App {
  Config.spinal.generateVerilog(BLS())
}

