package bls.src

import spinal.core._


// Hardware definition
case class BLS(N: Int = 384, MOD_PRIME: Int = 23) extends Component {

  val io = new Bundle {
    val i_val = in Bool()
    val i_rdy = in Bool()
    val i_ctl = in Bits(8 bits)
    val i_dat_a = in Bits(N bits)
    val i_dat_b = in Bits(N bits)
    val o_val = out Bool()
    val o_rdy = out Bool()
    val o_ctl = out Bits(8 bits)
    val o_dat = out Bits(2 * N bits)
  }

  val karatsuba = new KaratsubaMultiplier()

  karatsuba.io.i_val := io.i_val
  karatsuba.io.i_rdy := io.i_rdy
  karatsuba.io.i_dat_a := io.i_dat_a
  karatsuba.io.i_dat_b := io.i_dat_b
  io.o_val := karatsuba.io.o_val
  io.o_rdy := karatsuba.io.o_rdy
  io.o_dat := karatsuba.io.o_dat

  io.o_ctl := karatsuba.io.o_ctl
  karatsuba.io.i_ctl := io.i_ctl

}

object MyTopLevelVerilog extends App {
  Config.spinal.generateVerilog(BLS())
}

