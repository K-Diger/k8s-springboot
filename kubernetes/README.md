# Spring Boot Kubernetes Configuration

## 프로젝트 구조
```
.
├── README.md
├── kustomization.yml
├── base/
│   ├── namespace.yml
│   ├── deployment.yml
│   ├── service.yml
│   ├── hpa.yml
│   ├── network-policy.yml
│   ├── configmap.yml
│   ├── secrets.yml
│   ├── pod-disruption-budget.yml
│   └── service-monitor.yml
└── overlays/
    ├── dev/
    │   ├── kustomization.yml
    │   ├── configmap-patch.yml
    │   ├── secrets-patch.yml
    │   └── deployment-patch.yml
    └── prod/
        ├── kustomization.yml
        ├── configmap-patch.yml
        ├── secrets-patch.yml
        └── deployment-patch.yml
```

## 환경별 주요 차이점

### 개발 환경 (Development)
- JPA DDL-AUTO: update (개발 중 스키마 자동 업데이트)
- SQL 로깅 활성화
- DEBUG 레벨 로깅
- JWT 토큰 1시간 만료
- 리소스 제한 낮게 설정
- 디버깅 포트 개방 (5005)
- 평문 시크릿 사용 (개발 편의성)

### 운영 환경 (Production)
- JPA DDL-AUTO: validate (스키마 검증만)
- SQL 로깅 비활성화
- INFO/WARN 레벨 로깅
- JWT 토큰 24시간 만료
- 리소스 제한 높게 설정
- 운영 최적화 JVM 설정
- Base64 인코딩된 시크릿 사용

## 배포 방법

### 개발 환경 배포
```bash
kubectl apply -k overlays/dev
```

### 운영 환경 배포
```bash
kubectl apply -k overlays/prod
```

## 주요 설정 가이드

### 데이터베이스 설정
- 개발/운영 환경별로 다른 데이터베이스 호스트 사용
- 접속 정보는 Secret으로 관리
- Connection Pool 설정은 ConfigMap에서 관리

### JWT 설정
- 환경별로 다른 만료 시간 설정
- Secret Key는 Secret으로 관리
- 최소 32자 이상의 키 길이 사용

### 리소스 설정
- 개발 환경: CPU 200m-500m, Memory 512Mi-1Gi
- 운영 환경: CPU 500m-1000m, Memory 1Gi-2Gi

### 로깅 설정
- 개발 환경: DEBUG 레벨 상세 로깅
- 운영 환경: INFO/WARN 레벨 중요 로깅만

## 모니터링
- Prometheus 메트릭 수집 설정
- 액추에이터 엔드포인트 노출
- ServiceMonitor를 통한 메트릭 수집

## 보안
- 네트워크 정책으로 트래픽 제어
- 시크릿을 통한 민감 정보 관리
- Pod Security Standards 적용

## 가용성
- HPA를 통한 자동 스케일링
- PodDisruptionBudget으로 중단 관리
- Liveness/Readiness/Startup 프로브 설정