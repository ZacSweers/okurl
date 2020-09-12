import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "1.3.72"
  kotlin("kapt") version "1.3.72"
  `maven-publish`
  application
  id("net.nemerosa.versioning") version "2.12.1"
  id("com.diffplug.gradle.spotless") version "3.28.1"
  id("com.palantir.graal") version "0.7.1"
}

application {
  mainClassName = "com.baulsupp.okurl.MainKt"
}

repositories {
  mavenLocal()
  jcenter()
  mavenCentral()
  maven(url = "https://oss.sonatype.org/content/repositories/snapshots/")
  maven(url = "https://jitpack.io")
  maven(url = "https://repo.maven.apache.org/maven2")
  maven(url = "https://dl.bintray.com/kotlin/kotlin-eap/")
  maven(url = "https://dl.bintray.com/kotlin/kotlin-dev/")
  maven(url = "https://repo.spring.io/milestone/")
  maven(url = "https://dl.bintray.com/reactivesocket/RSocket/")
  maven(url = "https://oss.sonatype.org/content/repositories/releases/")
  maven(url = "https://dl.bintray.com/yschimke/baulsupp.com/")
  maven(url = "https://packages.atlassian.com/maven-public")
}

group = "com.github.yschimke"
description = "OkHttp Kotlin CLI"
version = versioning.info.display

base {
  archivesBaseName = "okurl"
}

java {
  sourceCompatibility = JavaVersion.VERSION_1_8
  targetCompatibility = JavaVersion.VERSION_1_8
}

tasks {
  withType(KotlinCompile::class) {
    kotlinOptions.jvmTarget = "1.8"
    kotlinOptions.apiVersion = "1.3"
    kotlinOptions.languageVersion = "1.3"

    kotlinOptions.allWarningsAsErrors = false
    kotlinOptions.freeCompilerArgs = listOf("-Xjsr305=strict", "-Xjvm-default=enable")
  }
}

tasks {
  withType(Tar::class) {
    compression = Compression.NONE
  }
}

tasks {
  withType(Tar::class) {
    compression = Compression.NONE
  }
}

graal {
  mainClass("com.baulsupp.okurl.MainKt")
  outputName("okurl")
  graalVersion("20.1.0")
  javaVersion("11")

  option("--enable-https")
  option("--no-fallback")
  option("--allow-incomplete-classpath")

//  if (Os.isFamily(Os.FAMILY_WINDOWS)) {
//    // May be possible without, but autodetection is problematic on Windows 10
//    // see https://github.com/palantir/gradle-graal
//    // see https://www.graalvm.org/docs/reference-manual/native-image/#prerequisites
//    windowsVsVarsPath('C:\\Program Files (x86)\\Microsoft Visual Studio\\2019\\BuildTools\\VC\\Auxiliary\\Build\\vcvars64.bat')
//  }
}

dependencies {
  implementation("com.github.yschimke:oksocial-output:5.6") {
    exclude(module = "svg-salamander")
    exclude(module = "jfreesvg")
  }
  implementation("com.baulsupp:okhttp-digest:0.4.0") {
    exclude(group = "com.squareup.okhttp3")
  }
  implementation("com.jakewharton.byteunits:byteunits:0.9.1")
  implementation("com.squareup.moshi:moshi:1.9.3")
  implementation("com.squareup.moshi:moshi-adapters:1.9.3")
  implementation("com.squareup.moshi:moshi-kotlin:1.9.3")
  implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")
  implementation("com.squareup.okhttp3:okhttp:4.9.0")
  implementation("com.squareup.okhttp3:okhttp-brotli:4.9.0")
  implementation("com.squareup.okhttp3:okhttp-dnsoverhttps:4.9.0")
  implementation("com.squareup.okhttp3:okhttp-sse:4.9.0")
  implementation("com.squareup.okhttp3:okhttp-tls:4.9.0")
  implementation("com.squareup.okio:okio:2.8.0")
  implementation("info.picocli:picocli:4.5.0")
  implementation("org.fusesource.jansi:jansi:1.18")
  implementation("io.jsonwebtoken:jjwt-api:0.10.6")
  implementation("io.jsonwebtoken:jjwt-impl:0.10.6")
  implementation("io.jsonwebtoken:jjwt-jackson:0.10.6")
  implementation("io.zipkin.brave:brave:5.7.0")
  implementation("io.zipkin.brave:brave-instrumentation-okhttp3:5.6.10") {
    exclude(group = "com.squareup.okhttp3")
  }
  implementation("io.zipkin.brave:brave-okhttp:4.13.6") {
    exclude(group = "com.squareup.okhttp3")
  }
  implementation("io.zipkin.java:zipkin:2.10.1")
  implementation("io.zipkin.reporter2:zipkin-sender-okhttp3:2.10.2") {
    exclude(group = "com.squareup.okhttp3")
  }
  implementation("org.conscrypt:conscrypt-openjdk-uber:2.4.0")
  implementation("org.jetbrains.kotlin:kotlin-reflect:1.3.72")
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.3.72")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.8")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-debug:1.3.8")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.3.8")
  implementation("org.slf4j:slf4j-api:2.0.0-alpha0")
  implementation("org.slf4j:slf4j-jdk14:2.0.0-alpha0")
  implementation("pt.davidafsilva.apple:jkeychain:1.0.0")
  implementation("com.formdev:svgSalamander:1.1.2.1")
  implementation("org.jfree:jfreesvg:3.4")
  implementation("org.brotli:dec:0.1.2")

  testImplementation("org.jetbrains.kotlin:kotlin-test:1.3.72")
  testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.3.72")
  testImplementation("com.squareup.okhttp3:mockwebserver:4.9.0")
  testImplementation("org.conscrypt:conscrypt-openjdk-uber:2.4.0")

  compileOnly("org.graalvm.nativeimage:svm:20.1.0")
  kapt("info.picocli:picocli-codegen:4.5.0")

  kapt("com.squareup.moshi:moshi-kotlin-codegen:1.9.3")
  implementation("io.github.classgraph:classgraph:4.8.87")

  implementation("io.swagger.parser.v3:swagger-parser:2.0.21")

  testRuntime("org.slf4j:slf4j-jdk14:2.0.0-alpha0")
}

val sourcesJar by tasks.creating(Jar::class) {
  classifier = "sources"
  from(kotlin.sourceSets["main"].kotlin)
}

val javadocJar by tasks.creating(Jar::class) {
  classifier = "javadoc"
  from("$buildDir/javadoc")
}

val jar = tasks["jar"] as org.gradle.jvm.tasks.Jar

publishing {
  repositories {
    maven(url = "build/repository")
  }

  publications {
    create("mavenJava", MavenPublication::class) {
      from(components["java"])
      artifact(sourcesJar)
      artifact(tasks.distTar.get())
    }
  }
}

spotless {
  kotlinGradle {
    ktlint("0.31.0").userData(mutableMapOf("indent_size" to "2", "continuation_indent_size" to "2"))
    trimTrailingWhitespace()
    endWithNewline()
  }
}

if (properties.containsKey("graal")) {
  val nativeImage = tasks["nativeImage"]

  distributions {
    val graal = create("graal") {
      contents {
        from("${rootProject.projectDir}") {
          include("README.md", "LICENSE")
        }
        from("${rootProject.projectDir}/zsh") {
          into("zsh")
        }
        into("bin") {
          from(nativeImage)
        }
      }
    }
  }
}
