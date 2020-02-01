// See LICENSE.SiFive for license details.

package freechips.rocketchip.stage.phases

import chisel3.stage.phases.Elaborate
import firrtl.AnnotationSeq
import firrtl.options.{Phase, PreservesAll}
import firrtl.options.Viewer.view
import freechips.rocketchip.stage.RocketChipOptions
import freechips.rocketchip.system.TestGeneration
import freechips.rocketchip.util.HasGeneratorUtilities

class GenerateTestSuiteMakefrags extends Phase with PreservesAll[Phase] with HasRocketChipStageUtils with HasGeneratorUtilities {

  override val prerequisites = Seq(classOf[Elaborate])

  override def transform(annotations: AnnotationSeq): AnnotationSeq = {
    val entOpts = view[RocketChipOptions](annotations)
    val targetDir = entOpts.targetDir.get
    val fileName = s"${getLongName(annotations)}.d"

    addTestSuites
    writeOutputFile(targetDir, fileName, TestGeneration.generateMakefrag)

    annotations
  }

}
