package com.github.aqn3130.navigatetodeclaration

import com.github.aqn3130.navigatetodeclaration.startup.GotoDeclarationHandler
import com.intellij.psi.PsiElement
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class HandlerTests : BasePlatformTestCase() {

    val goToHandler = GotoDeclarationHandler()

    /** Creates a .hbs virtual file and returns the first leaf PsiElement. */
    private fun hbsLeaf(content: String): PsiElement {
        val file = myFixture.configureByText("template.hbs", content)
        return file.firstChild
    }

    /** Creates a .kt virtual file (so the handler can find properties in it). */
    private fun addKotlinFile(fileName: String, content: String) {
        myFixture.addFileToProject(fileName, content)
    }

    fun testReturnsNullWhenNotFound() {
        hbsLeaf("name")
        addKotlinFile("testFileName", "name")

        val target = goToHandler.getGotoDeclarationTargets(
            sourceElement = null,
            offset = 0,
            editor = myFixture.editor
        )

        assertNull(target)
    }


}
