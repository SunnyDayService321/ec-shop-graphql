/**
 * U-AUTH-06: 会員情報更新処理
 */
async function handleUpdateProfile() {
    // 入力値の取得
    const email = document.getElementById('email').value.trim();
    const password = document.getElementById('password').value;
    const passwordConfirm = document.getElementById('passwordConfirm').value;

    const errorBox = document.getElementById('errorBox');
    const successBox = document.getElementById('successBox');

    if (errorBox) errorBox.style.display = 'none';
    if (successBox) successBox.style.display = 'none';

    // バリデーション
    if (!email && !password) {
        showError("更新する項目を入力してください");
        return;
    }

    if (password && (password.length < 8 || password !== passwordConfirm)) {
        showError(password.length < 8 ? "パスワードは8文字以上です" : "パスワードが一致しません");
        return;
    }

    // GraphQL送信
    try {
        const response = await fetch('/graphql', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                query: `
                    mutation ($input: UpdateProfileInput!) {
                        updateProfile(input: $input) { id email }
                    }
                `,
                variables: { input: { email, password, passwordConfirm } }
            })
        });

        const result = await response.json();

        if (result.errors) {
            showError("エラー: " + result.errors[0].message);
        } else {
            successBox.textContent = "更新しました。再読み込みします...";
            successBox.style.display = 'block';
            setTimeout(() => location.reload(), 1500);
        }
    } catch (err) {
        showError("通信に失敗しました");
    }
}

function showError(msg) {
    const errorBox = document.getElementById('errorBox');
    if (errorBox) {
        errorBox.textContent = msg;
        errorBox.style.display = 'block';
        errorBox.scrollIntoView({ behavior: 'smooth' });
    }
}
