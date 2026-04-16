package com.github.aqn3130.navigatetodeclaration

import com.github.aqn3130.navigatetodeclaration.startup.GotoDeclarationHandler
import com.intellij.psi.PsiElement
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class GotoDeclarationHandlerTest : BasePlatformTestCase() {

    private val handler = GotoDeclarationHandler()

    // ── helpers ──────────────────────────────────────────────────────────────

    /** Creates a .hbs virtual file and returns the first leaf PsiElement. */
    private fun hbsLeaf(content: String): PsiElement {
        val file = myFixture.configureByText("template.hbs", content)
        return file.firstChild
    }

    /** Creates a .kt virtual file (so the handler can find properties in it). */
    private fun addKotlinFile(fileName: String, content: String) {
        myFixture.addFileToProject(fileName, content)
    }

    // ── tests ─────────────────────────────────────────────────────────────────

    fun `test returns null when sourceElement is null`() {
        val result = handler.getGotoDeclarationTargets(null, 0, myFixture.editor)
        assertNull(result)
    }

    fun `test returns null for non-hbs files`() {
        // Configure a plain .txt file — handler should ignore it
        val file = myFixture.configureByText("SomeFile.txt", "myVar")
        val element = file.firstChild
        val result = handler.getGotoDeclarationTargets(element, 0, myFixture.editor)
        assertNull(result)
    }

    fun `test returns null for non-hbs kotlin files`() {
        val file = myFixture.configureByText("Main.kt", "val myVar = 1")
        val element = file.firstChild
        val result = handler.getGotoDeclarationTargets(element, 0, myFixture.editor)
        assertNull(result)
    }

    fun `test returns null when no matching kotlin property exists`() {
        // hbs file references a variable that has no kotlin counterpart
        addKotlinFile("src/Foo.kt", "val someOtherVar = \"hello\"")
        val element = hbsLeaf("unknownVar")
        val result = handler.getGotoDeclarationTargets(element, 0, myFixture.editor)
        assertNull(result)
    }

    fun `test resolves to matching top-level kotlin property`() {
        addKotlinFile("src/Greeting.kt", "val greeting = \"Hello, World!\"")
        val element = hbsLeaf("greeting")
        val result = handler.getGotoDeclarationTargets(element, 0, myFixture.editor)
        assertNotNull(result)
        assertTrue("Expected at least one result", result!!.isNotEmpty())
        assertEquals("greeting", (result[0] as? com.intellij.psi.PsiNamedElement)?.name)
    }

    fun `test resolves multiple matching properties across kotlin files`() {
        addKotlinFile("src/A.kt", "val title = \"A\"")
        addKotlinFile("src/B.kt", "val title = \"B\"")
        val element = hbsLeaf("title")
        val result = handler.getGotoDeclarationTargets(element, 0, myFixture.editor)
        assertNotNull(result)
        assertTrue("Expected two results", result!!.size >= 2)
    }

    fun `test resolves nested kotlin property`() {
        addKotlinFile(
            "src/Container.kt",
            """
            class Container {
                val label = "nested"
            }
            """.trimIndent()
        )
        val element = hbsLeaf("label")
        val result = handler.getGotoDeclarationTargets(element, 0, myFixture.editor)
        assertNotNull(result)
        assertTrue(result!!.isNotEmpty())
        assertEquals("label", (result[0] as? com.intellij.psi.PsiNamedElement)?.name)
    }

    fun `test returns null for blank hbs content`() {
        val file = myFixture.configureByText("template.hbs", "   ")
        val element = file.firstChild ?: return // whitespace-only file may have no children
        val result = handler.getGotoDeclarationTargets(element, 0, myFixture.editor)
        assertNull(result)
    }
}

