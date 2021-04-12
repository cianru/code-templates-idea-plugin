package createEmptyTemplate

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.project.impl.ProjectExImpl
import com.intellij.openapi.ui.Messages
import constant.Constants
import java.io.File

class CreateEmptyTemplate : AnAction() {

    override fun actionPerformed(event: AnActionEvent) {
        val basePath = (event.getData(CommonDataKeys.PROJECT) as ProjectExImpl).basePath
        val pathTemplate = File("$basePath${Constants.PATH_TEMPLATE}")
        var nameTemplate = Messages.showInputDialog("", "New Template", null) ?: return
        if (nameTemplate.isEmpty()) {
            nameTemplate = Constants.EMPTY_TEMPLATE_PATH_NAME
        }
        if (!pathTemplate.isDirectory) pathTemplate.mkdir()
        val pathNewTemplate = "${pathTemplate.path}/$nameTemplate"
        createPath(
            if (File(pathNewTemplate).isDirectory) "$pathNewTemplate${pathTemplate.list().size}"
            else pathNewTemplate
        )
    }

    private fun createPath(path: String) {
        File(path).mkdir()
        createMainFileTemplate(path)
    }

    private fun createMainFileTemplate(path: String) {
        val mainFile = File(path, Constants.MAIN_FILE_TEMPLATE)
        mainFile.createNewFile()
        val template = EmptyMainClassXml(name = path.split("/").last(), path = path)
        mainFile.writeText(createXmlFile(template))
    }

    private fun createXmlFile(template: EmptyMainClassXml) = XmlMapper().writeValueAsString(template)
        .replace("xmlns=\"\">", "xmlns=\"\">\n    ")
        .replace("</${Constants.TagXml.FIELD_NAME}>", "</${Constants.TagXml.FIELD_NAME}>\n    ")
        .replace("</${Constants.TagXml.FIELD_DESCRIPTION}>", "</${Constants.TagXml.FIELD_DESCRIPTION}>\n    ")
        .replace("</${Constants.TagXml.FIELD_PATH}>", "</${Constants.TagXml.FIELD_PATH}>\n")

}