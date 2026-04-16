package com.github.aqn3130.navigatetodeclaration.startup

import com.intellij.codeInsight.navigation.actions.GotoDeclarationHandler
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiManager
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope
import org.jetbrains.kotlin.idea.KotlinFileType
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtProperty

class GotoDeclarationHandler : GotoDeclarationHandler {

    override fun getGotoDeclarationTargets(
        sourceElement: PsiElement?,
        offset: Int,
        editor: Editor
    ): Array<PsiElement>? {
        if (sourceElement == null) return null

        // Only trigger in .hbs files
        val ext = sourceElement.containingFile?.virtualFile?.extension
        if (ext != "hbs") return null

        // Get the variable name the user clicked on
        val variableName = sourceElement.text
            .trim()
            .removePrefix("{{")
            .removeSuffix("}}")
            .trim()
            .takeIf { it.isNotBlank() } ?: return null

        val project = sourceElement.project
        val psiManager = PsiManager.getInstance(project)
        val scope = GlobalSearchScope.projectScope(project)

        // Search all Kotlin files for a top-level or member val/var with this name
        val results = mutableListOf<PsiElement>()

        FileTypeIndex.getFiles(KotlinFileType.INSTANCE, scope).forEach { vFile ->
            val ktFile = psiManager.findFile(vFile) as? KtFile ?: return@forEach
            // Search all properties in the file recursively
            collectMatchingProperties(ktFile, variableName, results)
        }

        return if (results.isNotEmpty()) results.toTypedArray() else null
    }

    private fun collectMatchingProperties(
        element: PsiElement,
        name: String,
        results: MutableList<PsiElement>
    ) {
        if (element is KtProperty && element.name == name) {
            results.add(element)
        }
        element.children.forEach { collectMatchingProperties(it, name, results) }
    }
}
