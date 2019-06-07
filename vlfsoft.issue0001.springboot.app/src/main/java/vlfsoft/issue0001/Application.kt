package vlfsoft.issue0001

import com.vaadin.flow.spring.annotation.EnableVaadin
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.event.EventListener
import java.awt.Desktop
import java.io.File
import java.net.URI

@ComponentScan("vlfsoft")
@EnableVaadin("vlfsoft")
@SpringBootApplication
open class VlpApplication {

    @Suppress("unused")
    @EventListener(ApplicationReadyEvent::class)
    fun applicationReadyEvent() {
        println("111")
        KDesktop.safeBrowse(URI("http://localhost:8080"))
    }

}

fun main(@Suppress("UnusedMainParameter") args: Array<String>) {
    println("Hello, world")

    SpringApplicationBuilder(VlpApplication::class.java)
            //.initializers(beans {  })
            //
            /**
             * To support [KDesktop]
             */
            .headless(false)
            //.profiles()
            .run()


}

object KDesktop {

    @JvmStatic
    inline fun safeAction(action: Desktop.Action, block: Desktop.() -> Boolean) =
            if (Desktop.isDesktopSupported()) {
                with(Desktop.getDesktop()) {
                    if (isSupported(action)) {
                        block()
                    } else {
                        false
                    }
                }
            } else {
                false
            }

    @JvmStatic
    fun safeOpen(file: File) = safeAction(Desktop.Action.OPEN) {
        open(file)
        true
    }

    fun safeOpen(filePath: String) = safeOpen(File(filePath))

    /**
     * KDesktop.safeBrowse(URI("http://localhost:8080"))
     */
    @JvmStatic
    fun safeBrowse(uri: URI) = safeAction(Desktop.Action.BROWSE) {
        browse(uri)
        true
    }

}
