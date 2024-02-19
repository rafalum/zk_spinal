package bls

import bls.dataTypes.JacobianPoint
import spinal.core._
import spinal.lib._
import spinal.lib.fsm._

class JacobianPointAdder(N: Int = 8, MOD_PRIME: Int = 107) extends Component {

  val io = new Bundle {
    val a = slave Stream JacobianPoint(N)
    val b = slave Stream JacobianPoint(N)
    val c = master Stream JacobianPoint(N)
  }

  val mul0 = new SimpleModuloMultiplier(N = N, MOD_PRIME = MOD_PRIME)
  val mul1 = new SimpleModuloMultiplier(N = N, MOD_PRIME = MOD_PRIME)
  val add0 = new SimpleModuloAdder(N = N, MOD_PRIME = MOD_PRIME)

  mul0.io.a := 0
  mul0.io.b := 0

  mul1.io.a := 0
  mul1.io.b := 0

  add0.io.a := 0
  add0.io.b := 0

  val a_reg = Reg(JacobianPoint(N))
  val b_reg = Reg(JacobianPoint(N))

  val out0 = Reg(UInt(N bits))
  val out1 = Reg(UInt(N bits))

  val u1 = Reg(UInt(N bits))

  val tmp0 = Reg(UInt(N bits))
  val tmp1 = Reg(UInt(N bits))
  val tmp2 = Reg(UInt(N bits))

  val h = Reg(UInt(N bits))

  io.a.ready := False
  io.b.ready := False

  io.c.valid := False
  io.c.payload.x := 0
  io.c.payload.y := 0
  io.c.payload.z := 0

  val fsm = new StateMachine {

    val Idle: State = new State with EntryPoint {
      whenIsActive {
        io.a.ready := True
        io.b.ready := True
        when(io.a.fire && io.b.fire) {
          a_reg.x := io.a.payload.x
          a_reg.y := io.a.payload.y
          a_reg.z := io.a.payload.z

          b_reg.x := io.b.payload.x
          b_reg.y := io.b.payload.y
          b_reg.z := io.b.payload.z

          goto(Stage0)
        }
      }
    }

    val Stage0: State = new State {
      whenIsActive {
        mul0.io.a := a_reg.z
        mul0.io.b := a_reg.z
        out0 := mul0.io.o       // z1z1

        mul1.io.a := b_reg.z
        mul1.io.b := b_reg.z
        out1 := mul1.io.o       // z2z2

        goto(Stage1)
      }
    }

    val Stage1: State = new State {
      whenIsActive {
        mul0.io.a := out0
        mul0.io.b := b_reg.x
        tmp0 := mul0.io.o     // u2

        mul1.io.a := out1
        mul1.io.b := a_reg.x
        u1 := mul1.io.o

        goto(Stage2)
      }
    }

    val Stage2: State = new State {
      whenIsActive {
        mul0.io.a := out0
        mul0.io.b := a_reg.z
        out0 := mul0.io.o     // z13

        mul1.io.a := out1
        mul1.io.b := b_reg.z
        out1 := mul1.io.o     // z23

        add0.io.a := tmp0
        add0.io.b := u1
        h := add0.io.o

        goto(Stage3)
      }
    }

    val Stage3: State = new State {
      whenIsActive {
        mul0.io.a := out0
        mul0.io.b := b_reg.y
        out0 := mul0.io.o     // S2

        mul1.io.a := out1
        mul1.io.b := a_reg.y
        out1 := mul1.io.o     // S1

        goto(Stage4)
      }
    }

    val Stage4: State = new State {
      whenIsActive {
        mul0.io.a := h
        mul0.io.b := h
        tmp0 := mul0.io.o     // H2

        add0.io.a := out0
        add0.io.b := out1
        tmp1 := add0.io.o     // R

        goto(Stage5)
      }
    }

    val Stage5: State = new State {
      whenIsActive {
        mul0.io.a := tmp0
        mul0.io.b := h
        out0 := mul0.io.o     // H3

        mul1.io.a := tmp1
        mul1.io.b := tmp1
        tmp2 := mul1.io.o     // R2

        goto(Stage6)
      }
    }

    val Stage6: State = new State {
      whenIsActive {
        mul0.io.a := out1
        mul0.io.b := out0
        out0 := mul0.io.o     // s1h3

        mul1.io.a := u1
        mul1.io.b := tmp0
        tmp0 := mul1.io.o     // u1h2

        add0.io.a := tmp2
        add0.io.b := out0
        tmp2 := add0.io.o     // r2h3

        goto(Stage7)
      }
    }

    val Stage7: State = new State {
      whenIsActive {
        mul0.io.a := a_reg.z
        mul0.io.b := b_reg.z
        out1 := mul0.io.o     // z1z2

        add0.io.a := tmp2
        add0.io.b := tmp0
        tmp2 := add0.io.o     // t1

        goto(Stage8)
      }
    }

    val Stage8: State = new State {
      whenIsActive {
        mul0.io.a := out1
        mul0.io.b := h
        out1 := mul0.io.o     // c.z

        add0.io.a := tmp2
        add0.io.b := tmp0
        tmp2 := add0.io.o     // c.x

        goto(Stage9)
      }
    }

    val Stage9: State = new State {
      whenIsActive {
        add0.io.a := tmp0
        add0.io.b := tmp2
        tmp0 := add0.io.o     // t2

        goto(Stage10)
      }
    }

    val Stage10: State = new State {
      whenIsActive {
        mul0.io.a := tmp1
        mul0.io.b := tmp0
        tmp0 := mul0.io.o     // t3

        goto(Stage11)
      }
    }

    val Stage11: State = new State {
      whenIsActive {
        add0.io.a := tmp0
        add0.io.b := out0
        tmp0 := add0.io.o     // c.y

        goto(Finish)
      }
    }

    val Finish: State = new State {
      whenIsActive {
        io.c.valid := True
        io.c.payload.x := tmp2
        io.c.payload.y := tmp0
        io.c.payload.z := out1

        when(io.c.fire) (goto(Idle))
      }

    }

  }



}
