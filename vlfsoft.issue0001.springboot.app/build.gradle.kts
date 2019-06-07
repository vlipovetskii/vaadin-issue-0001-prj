plugins {
    `java-library`
	
	application
	id("org.springframework.boot")
}

dependencies {
   
    // implementation: spring core specific features, depend on spring core framework
    implementation("org.springframework.boot:spring-boot-starter:${rootProject.extra["spring_bootVersion"]}")

    implementation("com.github.mvysny.karibudsl:karibu-dsl-v10:${rootProject.extra["karibudsl10Version"]}") {
        exclude("javax.servlet", "javax.servlet-api")
    }

    // implementation: Vaadin specific features, depend on vaadin flow framework
    // Tomcat by default
    implementation("com.vaadin:vaadin-spring-boot-starter:${rootProject.extra["springBootStarterVaadinVersion"]}") {
        exclude("javax.servlet", "javax.servlet-api")
    }

}

application {
    mainClassName = "vlfsoft.issue0001.ApplicationKt"
}

val jar by tasks.getting(Jar::class) {
    manifest {
        attributes["Main-Class"] = application.mainClassName
        attributes["Automatic-Module-Name"] = "vlfsoft.issue0001.springboot.app"
    }
}
