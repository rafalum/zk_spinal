package bls.src

import spinal.core._

class SimpleModuloAdder(N: Int, MOD_PRIME: Int) extends Component {

  val io = new Bundle {
    val a = in UInt(N bits)
    val b = in UInt(N bits)
    val o = out UInt(N bits)
  }


  when(io.b > io.a) {
    io.o := IntToUInt(MOD_PRIME).resize(N bits) - (io.b - io.a)
  }.otherwise {
    io.o := (io.a - io.b) % IntToUInt(MOD_PRIME).resize(N bits)
  }

}
