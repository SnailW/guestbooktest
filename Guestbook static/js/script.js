document.addEventListener('DOMContentLoaded', function(){
    // 방명록 로드
    loadEntries();

    // form submit event 처리
    document.getElementById('guestbookForm').addEventListener('submit', function(e){
        e.preventDefault();
        submitEntry();
    });

    // network info 표시
    fetchNetworkInfo();
});

// 방명록 항목 로드
function loadEntries(){
    fetch('/api/entries')
        .then(Response => {
            document.getElementById('responseTime').textContent = performance.now().toFixed(2);
            return Response.json();
        })
        .then(data => {
            displayEntries(data);
        })
        .catch(error => {
            console.error('data load failed:', error);
            document.getElementById('entriesList').innerHTML = '<p class="error">data load error</p>';
        });
}

// 방명록 항목 표시
function displayEntries(entries){
    const entriesList = document.getElementById('entriesList');

    if(entries.length===0){
        entriesList.innerHTML = '<p>guestbook is empty.</p>';
        return;
    }

    let html = '';
    entries.forEach(entry => {
        html += `
            <div class='entry'>
                <h3>${entry.name}</h3>
                <p class='message'>${entry.message}</p>
                <p class='meta'>작성일: ${entry.createdAt}</p>
            </div>`;
    });

    entriesList.innerHTML=html;
}

// 방명록 항목 제출
function submitEntry(){
    const name = document.getElementById('name').value;
    const message = document.getElementById('message').value;

    const data = {
        name: name,
        message: message
    };

    fetch('/api/entries', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(Response => Response.json())
    .then(result => {
        // form reset
        document.getElementById('name').value = '';
        document.getElementById('message').value = '';

        // 목록 다시 로드
        loadEntries();
    })
    .catch(error => {
        console.error('데이터 제출 실패:', error);
        alert('방명록 등록 실패');
    });
}

// 네트워크 정보 가져오기 (방화벽 테스트용)
function fetchNetworkInfo() {
    fetch('/api/network-info')
        .then(response => response.json())
        .then(data => {
            document.getElementById('clientIp').textContent = data.clientIp;
        })
        .catch(error => {
            console.error('네트워크 정보 가져오기 실패:', error);
        });
}