function performLogout() {
    // 1. 確認ダイアログを表示
    if (!confirm('ログアウトしますか？')) {
        return;
    }

    // 2. クライアント側のクリーンアップ
    // JWTトークンなどを削除（profile.jsなどとの兼ね合い）
    localStorage.removeItem('authToken');

    // 3. サーバーサイドのログアウト処理をPOSTで実行
    // index.html に定義した id="logoutForm" を取得
    const form = document.getElementById('logoutForm');

    if (form) {
        form.submit();
    } else {
        // 万が一フォームが見つからない場合は動的に生成して送信
        const dynamicForm = document.createElement('form');
        dynamicForm.method = 'POST';
        dynamicForm.action = '/account/logout';
        document.body.appendChild(dynamicForm);
        dynamicForm.submit();
    }
}

// ページの読み込み完了時に実行（必要に応じて追加の初期化を行う場合）
document.addEventListener('DOMContentLoaded', () => {
    console.log('logout.js loaded');
    // windowオブジェクトに明示的に紐付けて、どこからでも呼べるようにする
    window.performLogout = performLogout;
});
