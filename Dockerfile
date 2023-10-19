# 사용할 베이스 이미지
FROM openjdk:11

# 애플리케이션을 실행할 작업 디렉토리를 설정
WORKDIR /app

# 호스트 머신의 스프링 부트 애플리케이션 JAR 파일을 복사
ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} addp.jar
# 컨테이너에서 사용할 포트를 노출
EXPOSE 8080

# 애플리케이션을 실행하는 명령
CMD ["java", "-jar", "app.jar"]
