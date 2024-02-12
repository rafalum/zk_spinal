package bls

import spinal.core._

class SimpleModuloMultiplier(N: Int, MOD_PRIME: Int) extends Component {

  val io = new Bundle {
    val a: UInt = in UInt(N bits)
    val b: UInt = in UInt(N bits)
    val o: UInt = out UInt(N bits)
  }

  val result = (io.a * io.b) % IntToUInt(MOD_PRIME).resize(N bits)

  io.o := RegNext(result)

}
