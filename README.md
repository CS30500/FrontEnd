# SmartBottle - 스마트 물병 건강 관리 앱

<div align="center">
  <img src="app/src/main/res/mipmap-xxxhdpi/ic_launcher.png" alt="SmartBottle Logo" width="120"/>
  
  **건강한 수분 섭취 습관을 위한 스마트 물병 연동 안드로이드 앱**
  
  [![Android](https://img.shields.io/badge/Platform-Android-green.svg)](https://android.com)
  [![Kotlin](https://img.shields.io/badge/Language-Kotlin-blue.svg)](https://kotlinlang.org)
  [![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-orange.svg)](https://developer.android.com/jetpack/compose)
  [![API Level](https://img.shields.io/badge/API-26+-brightgreen.svg)](https://android-arsenal.com/api?level=26)
</div>

## 📋 목차

- [프로젝트 개요](#-프로젝트-개요)
- [주요 기능](#-주요-기능)  
- [기술 스택](#-기술-스택)
- [아키텍처](#-아키텍처)
- [시스템 요구사항](#-시스템-요구사항)
- [설치 및 설정](#-설치-및-설정)
- [사용법](#-사용법)
- [API 연동](#-api-연동)
- [하드웨어 연동](#-하드웨어-연동)
- [권한 관리](#-권한-관리)
- [프로젝트 구조](#-프로젝트-구조)
- [개발자 가이드](#-개발자-가이드)
- [문제 해결](#-문제-해결)
- [기여하기](#-기여하기)
- [라이선스](#-라이선스)

## 🎯 프로젝트 개요

SmartBottle은 IoT 스마트 물병과 연동하여 사용자의 수분 섭취량을 자동으로 추적하고 건강한 음수 습관을 형성할 수 있도록 도와주는 Android 애플리케이션입니다. 

### 핵심 가치
- **자동화된 수분 추적**: 스마트 물병을 통한 실시간 음수량 모니터링
- **개인화된 건강 관리**: 사용자 맞춤형 수분 섭취 목표 설정
- **스마트 알림 시스템**: 수분 부족 시 적절한 시점에 알림 제공
- **환경 데이터 통합**: 온도, 습도 등 환경 요인을 고려한 수분 권장량 계산

## ✨ 주요 기능

### 🏠 홈 화면
- **일일 수분 섭취 현황**: 원형 프로그레스바로 직관적인 진척도 표시
- **실시간 환경 데이터**: 
  - 외부 온도 (°C)
  - 물 온도 (°C) 
  - 습도 (%)
- **스마트 알림 목록**: 개인화된 수분 섭취 리마인더
- **디바이스 제어**: Start/Stop 버튼으로 스마트 물병 연결 관리

### 📊 히스토리 & 통계
- **월별 캘린더뷰**: 일별 수분 섭취 달성률 시각화
- **연속 달성 일수**: 목표 달성 연속 기록 추적 (Hydration Streak)
- **월간 통계**: 평균 달성률, 트렌드 분석
- **색상 코딩**: 달성률에 따른 시각적 구분 (0%, 25%, 50%, 75%, 100%)

### 👤 프로필 관리
- **개인정보 설정**: 나이, 키, 몸무게, 성별
- **Samsung Health 연동**: 건강 데이터 동기화
- **통계 요약**: 
  - 총 사용 일수
  - 최장 연속 달성 일수
  - 전체 평균 수분 섭취율

### 🔔 알림 시스템
- **실시간 푸시 알림**: Firebase Cloud Messaging 기반
- **스마트 리마인더**: 개인 패턴 분석 기반 알림
- **디바이스 연동**: 물병 진동 알림
- **방해 금지 모드**: 설정한 시간대 알림 차단

### 🔐 인증 시스템
- **사용자 계정 관리**: 회원가입/로그인
- **JWT 토큰 기반 인증**: 보안 강화된 API 통신
- **자동 로그인**: 토큰 기반 세션 유지

## 🛠 기술 스택

### Android Framework
- **Kotlin**: 주 개발 언어
- **Jetpack Compose**: 선언적 UI 개발
- **Navigation Compose**: 화면 간 내비게이션
- **Material Design 3**: Google 디자인 시스템

### 아키텍처 & 패턴
- **Clean Architecture**: 계층 분리 아키텍처
- **MVVM Pattern**: Model-View-ViewModel 패턴
- **Repository Pattern**: 데이터 추상화 계층
- **Dependency Injection**: Koin 라이브러리

### 데이터 관리
- **Room Database**: 로컬 데이터 저장소
- **SharedPreferences**: 설정값 저장
- **Kotlin Serialization**: JSON 직렬화

### 네트워킹
- **Ktor Client**: HTTP 클라이언트
- **OkHttp**: 네트워킹 라이브러리
- **RESTful API**: 백엔드 서버 통신

### IoT & 통신
- **Bluetooth Low Energy (BLE)**: 스마트 물병 연결
- **Firebase Cloud Messaging**: 푸시 알림
- **Foreground Service**: 백그라운드 디바이스 관리

## 🏗 아키텍처

### Clean Architecture 구조
```
SmartBottle/
├── app/
│   └── src/main/java/com/example/smartbottle/
│       ├── core/           # 공통 기능
│       │   ├── data/       # 데이터 구현 계층
│       │   ├── domain/     # 비즈니스 로직 계층
│       │   └── presentation/ # UI 계층
│       ├── auth/           # 인증 모듈
│       ├── water/          # 수분 추적 모듈
│       ├── history/        # 히스토리 모듈
│       ├── profile/        # 프로필 모듈
│       └── notification/   # 알림 모듈
```

### 의존성 주입 구조
- **CoreModule**: 네트워킹, BLE, 저장소
- **AuthModule**: 인증 관련 의존성
- **WaterModule**: 수분 추적 기능
- **HistoryModule**: 히스토리 관리
- **ProfileModule**: 사용자 프로필 관리

### 데이터 플로우
```
UI Layer (Compose) 
    ↓
ViewModel (State Management)
    ↓  
Repository (Data Abstraction)
    ↓
Data Sources (API/Local DB/BLE)
```

## 💻 시스템 요구사항

### Android 디바이스
- **Android 8.0 (API Level 26) 이상**
- **Bluetooth 4.0+ (BLE 지원)**
- **인터넷 연결** (Wi-Fi 또는 모바일 데이터)
- **위치 권한** (BLE 스캔용)
- **RAM 2GB 이상 권장**

### 하드웨어 요구사항
- **SmartBottle IoT 디바이스**
  - HM-10 Bluetooth 모듈 기반
  - 온도 센서
  - 수위 센서
  - 진동 모터

## 🚀 설치 및 설정

### 1. 프로젝트 클론
```bash
git clone https://github.com/your-username/SmartBottle.git
cd SmartBottle
```

### 2. Android Studio 설정
- **Android Studio Hedgehog (2023.1.1) 이상**
- **Kotlin 1.9.0 이상**
- **Gradle 8.0 이상**

### 3. 환경 설정
```bash
# local.properties 파일 생성
echo "sdk.dir=/path/to/your/android-sdk" > local.properties
```

### 4. Firebase 설정
1. [Firebase Console](https://console.firebase.google.com/)에서 프로젝트 생성
2. `google-services.json` 파일을 `app/` 디렉토리에 추가
3. Firebase Cloud Messaging 활성화

### 5. 백엔드 서버 설정
`NetworkConstants.kt`에서 서버 URL 수정:
```kotlin
object NetworkConstants {
    const val BASE_URL = "http://your-server-url:8000"
}
```

### 6. 빌드 및 실행
```bash
./gradlew assembleDebug
# 또는 Android Studio에서 Run 버튼 클릭
```

## 📱 사용법

### 초기 설정
1. **앱 설치 후 첫 실행**
2. **권한 승인**: 위치, 블루투스, 알림 권한 허용
3. **계정 생성**: 회원가입 또는 로그인
4. **프로필 설정**: 개인정보 입력 (나이, 키, 몸무게)

### 스마트 물병 연결
1. **물병 전원 켜기**: HM-10 모듈 활성화
2. **앱에서 Start 버튼** 클릭
3. **자동 페어링**: "HMSoft" 디바이스 검색 및 연결
4. **연결 확인**: 알림바에 "Smart Bottle" 서비스 표시

### 일상 사용
1. **물 섭취**: 스마트 물병으로 물을 마시면 자동 기록
2. **진행상황 확인**: 홈 화면에서 실시간 수분 섭취량 모니터링
3. **알림 받기**: 부족한 수분량에 대한 스마트 리마인더
4. **기록 조회**: 히스토리 탭에서 월별 통계 확인

## 🔌 API 연동

### 인증 API
```http
POST /auth/login
Content-Type: application/json

{
  "user_id": "username",
  "password": "password"
}
```

### 수분 섭취 기록 API
```http
POST /hydration/log
Authorization: Bearer {jwt_token}
Content-Type: application/json

{
  "amount": 250.0
}
```

### 일일 요약 API
```http
GET /hydration/today
Authorization: Bearer {jwt_token}

Response:
{
  "date": "2024-01-15",
  "total_intake_ml": 1500.0,
  "target_ml": 2000.0,
  "outsideTemperature": 22.5,
  "waterTemperature": 18.0,
  "humidity": 65.0
}
```

## 📡 하드웨어 연동

### BLE 통신 프로토콜
```
Device → App:
- TEMP:22.5    # 온도 데이터 (°C)
- DIST:150.0   # 음수량 데이터 (mL)

App → Device:
- BUZZ_ON\n    # 진동 알림 활성화
```

### 연결 과정
1. **BLE 스캔**: "HMSoft" 디바이스 검색
2. **GATT 연결**: Bluetooth GATT 프로토콜로 연결
3. **서비스 발견**: UUID `0000ffe0-0000-1000-8000-00805f9b34fb`
4. **Notification 활성화**: 실시간 데이터 수신
5. **데이터 파싱**: 수신된 문자열 파싱 후 서버 전송

## 🔐 권한 관리

### 필수 권한
```xml
<!-- 네트워크 통신 -->
<uses-permission android:name="android.permission.INTERNET"/>

<!-- 블루투스 통신 -->
<uses-permission android:name="android.permission.BLUETOOTH"/>
<uses-permission android:name="android.permission.BLUETOOTH_SCAN" />
<uses-permission android:name="android.permission.BLUETOOTH_CONNECT" />

<!-- 위치 서비스 (BLE 스캔용) -->
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>

<!-- 백그라운드 서비스 -->
<uses-permission android:name="android.permission.FOREGROUND_SERVICE"/>
<uses-permission android:name="android.permission.FOREGROUND_SERVICE_CONNECTED_DEVICE"/>

<!-- 푸시 알림 -->
<uses-permission android:name="android.permission.POST_NOTIFICATIONS"/>
```

### 런타임 권한 요청
앱 실행 시 `MainActivity`에서 자동으로 필요한 권한들을 요청합니다.

## 📁 프로젝트 구조

### 주요 파일 설명

#### Core Module
- `MainActivity.kt`: 메인 액티비티, 권한 관리
- `App.kt`: 애플리케이션 클래스, DI 초기화
- `CoreRepository.kt`: BLE 통신, 서버 API 인터페이스
- `BleManager.kt`: 블루투스 연결 관리

#### Water Module
- `HomeScreen.kt`: 메인 대시보드 화면
- `HomeViewModel.kt`: 수분 섭취 상태 관리
- `DailyHydration.kt`: 일일 수분 섭취 데이터 모델

#### History Module  
- `HistoryScreen.kt`: 월별 기록 화면
- `HistoryViewModel.kt`: 히스토리 데이터 관리

#### Auth Module
- `LoginScreen.kt`: 로그인 화면
- `AuthRepository.kt`: 인증 API 통신

#### Services
- `RunningService.kt`: BLE 연결 백그라운드 서비스
- `PushNotificationService.kt`: FCM 푸시 알림 처리

## 👨‍💻 개발자 가이드

### 개발 환경 설정
```bash
# 의존성 설치
./gradlew clean build

# 코드 스타일 검사
./gradlew ktlintCheck

# 테스트 실행
./gradlew test
```

### 주요 라이브러리 버전
```kotlin
// build.gradle.kts (Module: app)
dependencies {
    // Compose
    implementation("androidx.compose.bom:2024.02.00")
    
    // Koin DI
    implementation("io.insert-koin:koin-android:3.5.0")
    
    // Ktor
    implementation("io.ktor:ktor-client-core:2.3.7")
    
    // Room
    implementation("androidx.room:room-runtime:2.6.1")
    
    // Firebase
    implementation("com.google.firebase:firebase-messaging:23.4.0")
}
```

### 새로운 모듈 추가
1. 패키지 구조 생성: `data/`, `domain/`, `presentation/`, `di/`
2. Repository 인터페이스 정의
3. ViewModel 생성
4. DI 모듈 추가
5. `App.kt`에 모듈 등록

### BLE 통신 커스터마이징
```kotlin
// 새로운 명령어 추가 예시
coreRepository.sendCommandToDevice("CUSTOM_COMMAND\n")

// 새로운 데이터 형식 파싱
when {
    trimmed.startsWith("CUSTOM:") -> {
        val value = data.removePrefix("CUSTOM:").toFloatOrNull()
        // 처리 로직 구현
    }
}
```

## 🐛 문제 해결

### 일반적인 문제

#### BLE 연결 실패
- **증상**: 스마트 물병이 연결되지 않음
- **해결책**: 
  1. 위치 권한 확인
  2. 블루투스 활성화 확인
  3. 디바이스명 "HMSoft" 확인
  4. 앱 재시작

#### 푸시 알림 미수신
- **증상**: FCM 알림이 오지 않음
- **해결책**:
  1. `google-services.json` 파일 확인
  2. 알림 권한 승인 확인
  3. 백그라운드 활동 제한 해제

#### API 통신 오류
- **증상**: 서버 연결 실패 (401, 500 오류)
- **해결책**:
  1. 네트워크 연결 확인
  2. 서버 URL 및 포트 확인 (`NetworkConstants.kt`)
  3. JWT 토큰 만료 여부 확인

### 로그 확인
```bash
# Android 로그 확인
adb logcat | grep "SmartBottle\|BLE\|CoreRepository"

# 특정 태그 필터링
adb logcat -s "BleManager:D"
```

### 디버깅 팁
- BLE 연결 상태는 `RunningService` notification으로 확인
- API 응답은 Ktor logging으로 확인 가능
- Room 데이터베이스는 Android Studio Database Inspector 사용

## 🤝 기여하기

### 기여 방법
1. **Fork** 프로젝트
2. **Feature branch** 생성 (`git checkout -b feature/AmazingFeature`)
3. **Commit** 변경사항 (`git commit -m 'Add some AmazingFeature'`)
4. **Push** to branch (`git push origin feature/AmazingFeature`)
5. **Pull Request** 생성

### 코딩 컨벤션
- Kotlin 공식 스타일 가이드 준수
- 함수명은 camelCase, 클래스명은 PascalCase
- 주석은 KDoc 형식 사용
- 모든 public 함수에 대한 문서화

### 이슈 리포팅
버그 발견 시 다음 정보를 포함하여 이슈 등록:
- 디바이스 모델 및 Android 버전
- 앱 버전
- 재현 단계
- 예상 결과 vs 실제 결과
- 로그 또는 스크린샷

## 📄 라이선스

이 프로젝트는 MIT 라이선스 하에 배포됩니다. 자세한 내용은 [LICENSE](LICENSE) 파일을 참조하세요.

## 📞 연락처

- **프로젝트 관리자**: [이름](mailto:email@example.com)
- **개발팀**: [팀 이메일](mailto:team@example.com)
- **이슈 추적**: [GitHub Issues](https://github.com/your-username/SmartBottle/issues)

## 🙏 감사의 말

- Android Jetpack Compose 팀
- Koin 의존성 주입 프레임워크
- Ktor 네트워킹 라이브러리
- Firebase 플랫폼
- 모든 오픈소스 기여자들

---

<div align="center">
  <sub>Built with ❤️ for healthy hydration habits</sub>
</div> 