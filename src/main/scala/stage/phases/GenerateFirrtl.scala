// See LICENSE.SiFive for license details.

package freechips.rocketchip.stage.phases

import java.io.File

import chisel3.Driver
import chisel3.stage.ChiselCircuitAnnotation
import chisel3.stage.phases.Elaborate
import firrtl.AnnotationSeq
import firrtl.options.{Phase, PreservesAll}
import firrtl.options.Viewer.view
import freechips.rocketchip.stage.RocketChipOptions

class GenerateFirrtl extends Phase with PreservesAll[Phase] with HasRocketChipStageUtils {

  override val prerequisites = Seq(classOf[Elaborate])

  override def transform(annotations: AnnotationSeq): AnnotationSeq = {
    val rOpts = view[RocketChipOptions](annotations)
    val targetDir = rOpts.targetDir.get
    val file = new File(targetDir, s"${getLongName(annotations)}.fir")

    annotations.flatMap {
      case a: ChiselCircuitAnnotation =>
        Driver.dumpFirrtl(a.circuit, Some(file))
        Some(a)
      case a => Some(a)
    }
  }

}
