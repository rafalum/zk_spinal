package bls.src

import spinal.core._

// Define a BlackBox for the montgomery_multiplier Verilog component
class KaratsubaMultiplier(N: Int = 384, CTL: Int = 8, LEVEL: Int = 4) extends BlackBox {
  // Set the generic parameter N

  val generic = new Generic {
    val BITS = KaratsubaMultiplier.this.N
    val CTL_BITS = KaratsubaMultiplier.this.CTL
    val LEVEL = KaratsubaMultiplier.this.LEVEL
  }

  // Define the IO of the BlackBox, matching the Verilog component
  val io = new Bundle {
    val i_clk = in Bool()
    val i_rst = in Bool()
    val i_val = in Bool()
    val i_rdy = in Bool()
    val i_ctl = in Bits(CTL bits)
    val i_dat_a = in Bits(N bits)
    val i_dat_b = in Bits(N bits)
    val o_val = out Bool()
    val o_rdy = out Bool()
    val o_ctl = out Bits(CTL bits)
    val o_dat = out Bits(2 * N bits)
  }

  // Map the current clock domain to the clk and rst signals of the BlackBox
  // Assuming active high reset
  mapCurrentClockDomain(clock = io.i_clk, reset = io.i_rst)

  noIoPrefix()
  addRTLPath("./hw/verilog/KaratsubaMultiplier.v")
}