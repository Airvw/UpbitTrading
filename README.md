# 업비트 트레이딩 시스템
## 요구사항
* KRW 시장의 코인 중 RMI가 30이하인 코인 조회하기
## 기능 목록
* KRW 시장의 모든 코인 조회하기 -> 코인 리스트로 파싱
* 현재가 조회를 코인 리스트를 활용해서 조회 ~~구분자는 ','(콤마)~~ ~~&rightarrow; 현재가로 오늘 가격으로~~ 전날과의 가격변화를 알려주는 change_price를 활용해서 RMI 지표를 확인할 생각
* KRW 시장의 모든 코인의 일단위로 시세 조회하기
* 조회된 시세를 RMI 지표로 계산
* RMI 지표가 30 이하인 코인 리스트 생성하기

## 이슈
* [RSI 공식 이슈](https://blog.naver.com/PostView.naver?blogId=dandanhandol&logNo=222463407623&redirect=Dlog&widgetTypeCall=true&directAccess=false)
* count 200개 제한으로 데이터 부족함 : 상장날부터의 가격 데이터를 가져오기(query param의 to를 활용하기)