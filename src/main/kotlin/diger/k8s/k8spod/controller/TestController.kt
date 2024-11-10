package diger.k8s.k8spod.controller

import java.time.LocalDateTime
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api")
class TestController {

    @Value("\${POD_NAME:unknown}")
    private val podName: String? = null

    @GetMapping("/test")
    fun test(): Map<String, String?> {
        return java.util.Map.of(
            "message", "Hello from Pod",
            "podName", podName,
            "timestamp", LocalDateTime.now().toString()
        )
    }
}
