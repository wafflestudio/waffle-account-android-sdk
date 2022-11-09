import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.archivesName

plugins {
    `java-library`
    `maven-publish`
    signing
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
    id("com.android.application") version "7.3.1" apply false
    id("com.android.library") version "7.3.1" apply false
    id("org.jetbrains.kotlin.android") version "1.7.20" apply false
}

group = "com.wafflestudio.account"
version = "0.0.1a"

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            pom {
                name.set("waffle-account-sdk")
                description.set("Wafflestudio SSO SDK")
                url.set("https://github.com/wafflestudio/waffle-account-sdk-android")
                licenses {
                    license {
                        name.set("MIT")
                        url.set("http://opensource.org/licenses/MIT\"")
                    }
                }
                developers {
                    developer {
                        id.set("Hank-Choi")
                        name.set("Hank-Choi")
                        email.set("zlzlqlzl1@naver.com")
                    }
                }
                scm {
                    connection.set("scm:git@github.com:wafflestudio/waffle-account-sdk-android")
                    developerConnection.set("scm:git@github.com:wafflestudio/")
                    url.set("https://github.com/wafflestudio/waffle-account-sdk-android")
                }
            }
        }
    }
}

signing {
    sign(publishing.publications["mavenJava"])
}

nexusPublishing {
    repositories {
        sonatype {
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
            username.set(project.property("sonatypeUsername").toString())
            password.set(project.property("sonatypePassword").toString())
        }
    }
}
//apply{
//    plugin("maven")
//    plugin("signing")
//}

//tasks.javadocJar(typ: Jar) {
//    classifier = 'javadoc'
//    from javadoc
//}
//
//task sourcesJar(type: Jar) {
//    classifier = 'sources'
//    from sourceSets.main.allSource
//}
