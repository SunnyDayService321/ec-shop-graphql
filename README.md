# README

# EC-Shop Project (Spring Boot + Docker Template)

このリポジトリは、Docker環境（Java/MySQL/Nginx）で動作する**本格的なECサイト（電子商取引）開発用テンプレート**です。
単純なCRUDアプリから、ユーザー・管理者の権限管理を含む実用的なWebアプリケーションへの拡張を目的としています。

## 🚀 プロジェクトの目的
現在、基本的な商品管理機能（CRUD）をベースに、以下のECサイト機能の実装を進めています。

- **ユーザー機能**: 商品閲覧、カート投入、非同期「お気に入り」登録（Ajax実装済み）。
- **管理者機能**: 商品登録・編集・在庫管理。
- **認証・認可**: Spring Securityを用いた「ユーザー/管理者」のログイン切り替え。

## 🏗 システム構成図 (Architecture)



- **Frontend**: Thymeleaf, JavaScript (Ajax/Fetch API)
- **Backend**: Spring Boot (Java 11+), Spring Data JPA
- **Database**: MySQL (永続化ボリューム設定済み)
- **Infrastructure**: Docker Compose, Nginx (Reverse Proxy)

## 🛠 実装予定の機能 (Roadmap)

### 1. 認証機能 (Auth)
- Spring Securityを導入し、`login.html` を作成。
- ユーザーロール（USER / ADMIN）によるアクセス制御。

### 2. 管理者向け画面 (Admin Dashboard)
- `/admin/**` パスの保護。
- 商品の一括管理、売上確認用ダッシュボードの構築。

### 3. ユーザー向け画面 (User Portal)
- 商品一覧および詳細表示。
- カート機能（Session管理）。
- 購入履歴の表示。

## 📋 セットアップ手順

### 1. 起動方法
`docker` フォルダへ移動し、ビルドと起動を行います。
```bash
cd docker
docker-compose up -d --build
