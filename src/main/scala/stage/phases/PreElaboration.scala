// See LICENSE.SiFive for license details.

package freechips.rocketchip.stage.phases

import chisel3.RawModule
import chisel3.stage.ChiselGeneratorAnnotation
import firrtl.AnnotationSeq
import firrtl.options.{Phase, PreservesAll}
import freechips.rocketchip.config.Parameters
import freechips.rocketchip.diplomacy._
import freechips.rocketchip.util.HasGeneratorUtilities

class PreElaboration extends Phase with PreservesAll[Phase] with HasRocketChipStageUtils with HasGeneratorUtilities {

  override val prerequisites = Seq(classOf[Checks])

  override def transform(annotations: AnnotationSeq): AnnotationSeq = {

    val top = Class.forName(s"${getFullTopModuleClass(annotations)}")
    logger.info(s"$top")

    val config = getConfig(getFullConfigClasses(annotations))
    logger.info(s"$config")

    val gen = () =>
      top
        .getConstructor(classOf[Parameters])
        .newInstance(config) match {
          case a: RawModule => a
          case a: LazyModule => LazyModule(a).module
        }

    ChiselGeneratorAnnotation(gen) +: annotations
  }

}
