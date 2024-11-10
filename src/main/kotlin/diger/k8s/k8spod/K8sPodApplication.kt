package diger.k8s.k8spod

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class K8sPodApplication

fun main(args: Array<String>) {
    runApplication<K8sPodApplication>(*args)
}
