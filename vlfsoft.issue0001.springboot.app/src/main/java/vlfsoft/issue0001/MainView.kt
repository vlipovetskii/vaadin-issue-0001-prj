package vlfsoft.issue0001

import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.formlayout.FormLayout
import com.vaadin.flow.component.html.Anchor
import com.vaadin.flow.component.icon.Icon
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.Route
import com.vaadin.flow.server.AbstractStreamResource
import com.vaadin.flow.server.InputStreamFactory
import com.vaadin.flow.server.StreamResource
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

@Suppress("unused")
@Route
class MainView : VerticalLayout() {

    init {

        label("issue0001")

        formLayout {

            button("Test1")

            "test-file.txt".also {
                anchorToDownloadWithButton(File(it).toStreamResource(), buttonText = it)
            }

        }

        formLayout {

            responsiveSteps = listOf(FormLayout.ResponsiveStep("0", 1), FormLayout.ResponsiveStep("10em", 2))

            button("Test1")
            button("Test2")

            "test-file.txt".also {
                anchorToDownloadWithButton(File(it).toStreamResource(), buttonText = it)
            }

        }


    }

}

// Anchor

fun (@VaadinDsl HasComponents).anchor(href: AbstractStreamResource, anchorText: String? = null, block: (@VaadinDsl Anchor).() -> Unit = {}) =
        init(Anchor(href, anchorText), block)

// Vaadin 10 Let user download a file https://vaadin.com/forum/thread/17010389
// To extend Html.kt
fun (@VaadinDsl HasComponents).anchorToDownload(href: AbstractStreamResource, anchorText: String? = null, block: (@VaadinDsl Anchor).() -> Unit = {}) =
        anchor(href, anchorText) {
            element.setAttribute("download", true)
            block()
        }

/**
 * [buttonIcon] VaadinIcon.DOWNLOAD.create()
 */
fun (@VaadinDsl HasComponents).anchorToDownloadWithButton(href: AbstractStreamResource, buttonIcon: Icon? = VaadinIcon.DOWNLOAD.create(), buttonText: String? = null, anchorText: String? = null, block: (@VaadinDsl Anchor).() -> Unit = {}): Button {
    var downloadButton: Button? = null
    anchorToDownload(href, anchorText) {
        downloadButton = button(buttonText, buttonIcon) {
        }
        block()
    }
    return downloadButton!!
}

fun String.createResource(getStream: () -> InputStream) = StreamResource(this, InputStreamFactory {
    return@InputStreamFactory getStream()
})

fun String.createResourceFromByteArray(getStream: () -> ByteArray) = createResource { ByteArrayInputStream(getStream()) }

fun File.toStreamResource(altFile: File? = null) =
        if (altFile == null || exists())
            name.createResource { FileInputStream(this) }
        else
        // Don't call toStreamResource recursively, to avoid infinite recursion if altFile doesn't exist
            altFile.run { name.createResource { FileInputStream(this) } }
