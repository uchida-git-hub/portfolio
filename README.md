# プログラミングに関する勉強の実績報告と質問を行えるSNSアプリケーション

　みんなが何を勉強しているのか知りたい、プログラミングの勉強を頑張っているのは自分ひとりじゃない、みんなでモチベーションを高めあうを目指して作成しました。
　このアプリケーションでは自分の勉強実績の投稿と質問の投稿、それらに対するコメントを送信することができます。また、一緒にモチベーションを高めたいと感じたユーザーをフォローすることでタイムラインにフォローしているユーザーの投稿を表示することができます。

## 作成した経緯
　プログラミングを学習する際に、学生時代と異なり、周りにプログラミングを勉強しているひとがいなかった。
　ある時、ふとSNSで目にしたプログラミングを学習中の方の投稿を目にして「自分も頑張ろう」と思えた。それをきっかけにプログラミングの学習を専門とするSNSを作成し、自分以外のプログラミングを学習中の方々のモチベーションを高める場を作りたいと思うようになり、ポートフォリオとして作成した。

## 使用した技術
 - JAVA17.0.10_7
 - SPRINGBOOT3.1.8
   - Spring Security
   - mybatis3.0.3
 - HTML
   - Thymeleaf
 - CSS
   - Bootstrap5.3.2
 - MYSQL8.0
 - Tomcat
 - Apache
 - AWS
   - EC2
   - RDS
   - ELB
   - Route53
   - ACM
## インフラ

## データベースの設計

## 機能一覧

## 工夫した点


## 反省点
- adminユーザーを作成する方法とセキュリティの両立が難しそうだったため、データベースを直接操作してadminユーザーを作成した。adminユーザーのみadminユーザーを作成可能にするなどの機能を追加するべきだった
- おそらく読み込み速度等が原因でボタンをダブルクリックされるとエラー画面に遷移する。登録などが2回行われることはないが、本番リリースでこのような不具合は望ましくない。javascript等を使って複数回のクリックに対し、エラーが発生しないようにしたい。
- テストコードなどについて学習できていないため、機能を追加し、実際に手動で動かして問題がないかの確認を行った。テスト手法やテスト用のコードの書き方についても学習したい。
- 一部機能についてSQLがデータベース内のすべてのデータを結合してから条件に合うデータを抽出する設計になっている。蓄積されるデータが多くなることを考え、データの結合数を少なくできるよう改善が必要。
- データベースへのアクセスを複数回行うようになっている機能がある。データベースへのアクセスは負荷が大きい処理のため、データの結合等を用いて少ない回数のアクセスで必要なデータを抽出できるようにしたい。
- データベースからデータを取得する際に不要なデータを取得している部分がある。必要なデータのみを取得するようにプログラムするべきだった。
## 今後追加したい機能
- 画像の投稿機能
- adminユーザーの作成のフローの作成。
- １ユーザー辺りのセッション数の管理。
- コメントやフォローに対する通知機能の追加。
- ブロック機能の追加。
- 画面サイズがパソコンのフルスクリーンでない場合に、画面のボタンの配置等が正しく配置されるように改善したい。
