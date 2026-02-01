/**
 * お気に入り状態を非同期で切り替える
 * @param {number} id - 商品ID
 * @param {HTMLElement} buttonElement - クリックされたボタン要素
 */
function toggleFavorite(id, buttonElement) {
    const starSpan = buttonElement.querySelector('.star-icon');

    // Fetch APIを使用してサーバーにPOSTリクエストを送信
    // URLの先頭に / を付けてルートパスから指定します
    fetch('/item/itemlists/' + id + '/favorite', {
        method: 'POST'
    })
    .then(response => {
        if (response.ok) {
            // サーバーから届いた最新の状態（true/false）を取得
            return response.json();
        } else {
            throw new Error('サーバーエラー: ' + response.status);
        }
    })
    .then(isFavorite => {
        // サーバーの返答通りに見た目を更新
        starSpan.innerText = isFavorite ? '★' : '☆';
        starSpan.style.color = isFavorite ? '#ffcc00' : '#ccc';
    })
    .catch(error => {
        console.error('Error:', error);
        alert('お気に入りの保存に失敗しました。');
    });
}
