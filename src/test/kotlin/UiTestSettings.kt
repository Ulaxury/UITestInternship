import com.intellij.driver.sdk.ui.components.common.ideFrame
import com.intellij.driver.sdk.ui.components.elements.checkBox
import com.intellij.driver.sdk.ui.components.elements.tree
import com.intellij.driver.sdk.ui.xQuery
import com.intellij.ide.starter.driver.engine.runIdeWithDriver
import com.intellij.ide.starter.ide.IdeProductProvider
import com.intellij.ide.starter.models.TestCase
import com.intellij.ide.starter.project.LocalProjectInfo
import com.intellij.ide.starter.runner.Starter
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class UiTestSettings {
    @Test
    fun openEditorCreateChangelistsAutomatically() {

        val testContext =
            Starter.newContext(
                "testExample",
                TestCase(
                    IdeProductProvider.IC,
                    LocalProjectInfo(Path("src/test/resources/test-projects/simple-project"))
                ).withVersion("2024.3")
            ) //1&2

        testContext.runIdeWithDriver().useDriverAndCloseIde {
            ideFrame {
                openSettingsDialog() //3

                val mySettings = tree("//div[@class='MyTree']")

                fun clickVersionControlAndChangelists() = mySettings.clickPath("Version Control", "Changelists")

                clickVersionControlAndChangelists() //4

                val myCheckBox = checkBox(xQuery { byVisibleText("Create changelists automatically") })

                myCheckBox.click() //5

                assertTrue(myCheckBox.isSelected()) { "Not Selected" } //6

                fun clickOK() = x(xQuery { byVisibleText("OK") }).click()

                clickOK() //7
            }
        }
    }
}
