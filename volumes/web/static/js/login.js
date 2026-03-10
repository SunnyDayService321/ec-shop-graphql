document.addEventListener('DOMContentLoaded', () => {
    const loginForm = document.getElementById('loginForm');

    if (loginForm) {
        loginForm.addEventListener('submit', async (e) => {
            e.preventDefault();

            const email = document.getElementById('email').value;
            const password = document.getElementById('password').value;

            try {
                // GraphQL Mutation の実行
                const response = await fetch('/graphql', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({
                        query: `
                            mutation Login($email: String!, $password: String!) {
                                login(email: $email, password: $password) {
                                    token
                                    user { id email }
                                }
                            }
                        `,
                        variables: { email, password }
                    })
                });

                const result = await response.json();

                if (result.data && result.data.login) {
                    const loginData = result.data.login;
                    const token = result.data.login.token;
                    const userId = result.data.login.user.id; // IDを取得

                    // ★追加：Javaのセッションにも教える
                    await fetch('/api/session/sync', {
                        method: 'POST',
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify({ userId: userId, token: token })
                    });

                    localStorage.setItem('authToken', token);
                    alert('ログインに成功しました！');
                    window.location.href = '/account/profile'; // マイページへ
                } else {
                    // 認証失敗時の処理
                    alert('ログインに失敗しました。メールアドレスとパスワードを確認してください。');
                }
            } catch (error) {
                console.error('通信エラー:', error);
                alert('サーバーとの通信に失敗しました。');
            }
        });
    }
});
