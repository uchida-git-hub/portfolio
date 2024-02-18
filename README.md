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
#### ログイン関係の機能
- アカウント登録機能
- パスワードを3回以上間違えた場合にアカウントをロックする機能
- 退会機能
#### ユーザーに関する機能
- プロフィールの変更機能。
- 他のユーザーのプロフィール画面を表示する機能。この画面からフォローの機能を利用できる。
- 他のユーザーをフォローする機能。
- フォローを解除する機能。
- 自分がフォローしているユーザーの一覧を表示する機能。
- 自分をフォローしているユーザーの一覧を表示する機能。
#### 投稿に関する機能
- つぶやきを投稿する機能
- 投稿を編集する機能（投稿を行ったアカウントでのみ実行可能）
- 投稿を削除する機能（投稿を行ったアカウントでのみ実行可能）
- 投稿に対していいねをつける機能（投稿を行ったアカウント以外のアカウントでのみ実行可能）
- いいねの取り消し機能
- 投稿の詳細を表示する機能。詳細画面からいいねやコメントの投稿、コメントの閲覧が行える。
- 投稿に対してコメントを投稿する機能
- 投稿に対するコメントを削除する機能（コメントを投稿したユーザーでのみ実行可能）
#### 投稿の表示に関する機能
- フォローしているユーザーの投稿をタイムラインとして表示する機能。
- 投稿をキーワード、技術のカテゴリ、投稿のカテゴリで検索する機能。
- いいねした投稿の一覧を表示する機能。
- 
#### 管理者専用機能
- アカウントを管理者として停止する機能。停止されたアカウントはアカウント検索等の機能で表示されなくなり、コメント、投稿も同様に表示されなくなる
- アカウントの停止の解除。上記機能で停止したアカウントの停止を解除する。
- アカウントのロックの解除。パスワードを3回以上ミスした場合のロックを解除する機能。
- ロック（パスワードを3回以上ミスした場合）中のアカウントと停止（管理者としてアカウントを停止）中のアカウントの一覧を表示する機能。
- 投稿を管理者として削除する機能。
## 工夫した点
- 意図しない操作が実行されることがないように注意してプログラミングを行いました。変更や削除の処理に関しては何らかの方法で不正なユーザーから操作が行われないよう、JAVAのコード内でも認証情報を参照して適切なユーザーによる操作かを確認した。
- 攻撃的な投稿が行われないように投稿内容にバリデーションを用いていくつかの不適切な単語が使われないようにした。
- なるべく中間テーブルなどを使用してデータベースから取得するデータがソースコードに依存しないようにした。
- 可能な限り、テーブルの結合やサブクエリを利用してデータベースへのアクセス回数や処理を行うデータの総数を減らすようにした。

## 反省点
- adminユーザーを作成する方法とセキュリティの両立が難しそうだったため、データベースを直接操作してadminユーザーを作成した。adminユーザーのみadminユーザーを作成可能にするなどの機能を追加するべきだった
- おそらく読み込み速度等が原因でボタンをダブルクリックされるとエラー画面に遷移する。登録などが2回行われることはないが、本番リリースでこのような不具合は望ましくない。javascript等を使って複数回のクリックに対し、エラーが発生しないようにしたい。
- テストコードなどについて学習できていないため、機能を追加し、実際に手動で動かして問題がないかの確認を行った。テスト手法やテスト用のコードの書き方についても学習したい。
- 一部機能についてSQLがデータベース内のすべてのデータを結合してから条件に合うデータを抽出する設計になっている。蓄積されるデータが多くなることを考え、データの結合数を少なくできるよう改善が必要。
- データベースへのアクセスを複数回行うようになっている機能がある。データベースへのアクセスは負荷が大きい処理のため、データの結合等を用いて少ない回数のアクセスで必要なデータを抽出できるようにしたい。
- データベースからデータを取得する際に不要なデータを取得している部分がある。必要なデータのみを取得するようにプログラムするべきだった。
- パスワード変更機能について、パスワードの有効期限が切れた場合と任意に変更したい場合のページが同じになっているため、ホームに戻る機能やサイドバーに表示ができない仕様になってしまった。
- コメントを管理者として削除する機能を実装していなかった。
- データベースから取得するデータの数に上限を設けていなかった。扱うデータの総数が多くなる場合は上限を設け、実行メモリを消費しすぎないようにするべきだった。
- バリデーションからのメッセージをプロパティファイルなどに記述し、変更と多言語への対応を実装しやすくするべきだった。
## 今後追加したい機能
- 画像の投稿機能
- adminユーザーの作成のフローの作成。
- １ユーザー辺りのセッション数の管理。
- コメントやフォローに対する通知機能の追加。
- ブロック機能の追加。
- 画面サイズがパソコンのフルスクリーンでない場合に、画面のボタンの配置等が正しく配置されるように改善したい。
- 任意にパスワードの変更を行う専用のHTMLファイルを作成してパスワード変更ページにサイドバーやホームに戻る機能を追加したい。
- aws上に各ユーザーのログを記録する機能。

## 感想
　実際にアプリケーションを制作してみて、反省するべき点が多かったと感じました。作成に入る前の段階で細かい仕様を考えて問題点がないか考えることにもっと多くの時間を使っていれば反省点がここまで多くなることも無かったのではないかと思います。
 また、テストの方法についても学習してからプログラミングを行えば、各機能の確認ももっと正確に、短時間で行えたのではないかと思います。
 プログラミング中は行き詰ることもありましたが、それらを調べたりする時間も含めてとても楽しかったです。
 今後は保守運用のしやすいきれいなコードを目指し、適切なコメントアウトの作成方法や読みやすいコードの書き方についても学習したいと思いました。
 アプリケーションを作成すると、もっと身に着けたい技術や、気になることがどんどん出てきて非常に有益だったと思います。