# Umemory
単語帳、記憶お助けアプリです。  
忘却曲線を利用し間隔を開けて表示してくれるので、長期記憶をしたいことを気軽に復習できます。  
firebaseを利用しているので、友達とも共有が可能です。

<img src ="https://user-images.githubusercontent.com/66674598/94353287-57a7ff00-00aa-11eb-84fe-08c179683e15.png" width="200">

# 使用技術
- Kotlin 1.3.7
- Realm 6.0.2
- Firebase
  - Authentication
  - Cloud Firestore
- Preference 1.0.0
- Espresso 3.0.2

# 機能一覧  
- ログイン機能(firebase-Authentication)
- 復習機能(Realm,firebase-Cloud Firestore,フラグメント)
- 履歴機能(Realm,firebase-Cloud Firestore,フラグメント)
- 登録機能(Realm,firebase-Cloud Firestore,フラグメント)
- ランダム機能(共有プリファレンス,フラグメント)  

# テスト
- Espresso
