//서버 주소 변수
//var ServerUrl = "http://13.125.114.252";
//  var ServerUrl = "http://localhost:8080";
// var ServerUrl = "http://13.125.114.252:8080";
var ServerUrl = "https://api.piium.co.kr";
// var ServerUrl = "https://market5478.com:8080";
var currentDate = '';
var currentTime = '';
var firstCategory;
var address;
var title;
var desc;
var shareImg;



$( document ).ready(function() {

// 현재시간
   currentDate = new Date();
   currentTime += currentDate.getHours();
   currentTime += currentDate.getMinutes();
});

// 파라미터 받기
function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}
// 영업 여부
function isSopen(sthm, edhm){

    if( sthm <= currentTime && currentTime  < edhm ){
        return "영업중";
    }
    else{
        return "영업종료";
    }
}
//영업일 문자 치환
function openDay(num){
    switch(num){
        case '1':
            return '월';
            break;
        case '2':
            return '화';
            break;
        case '3':
            return '수';
            break;
        case '4':
            return '목';
            break;
        case '5':
            return '금';
            break;
        case '6':
            return '토';
            break;
        case '7':
            return '일';
            break;
    }
}
//날짜 변환
function changeDateString(date){
    var year = date.substr(2,2);
    var month = date.substr(4,2);
    var day = date.substr(6,2);
    return year + "." + month + "." + day
}
//문자열 사이사이에 쉼표 넣기
function addComma(str){
    var len = str.length;
    var resultStr = '';
    if(len >= 1){
        for(var i = 0; i < len; i++){
            var s = str.substr(i,1);
            if(i == len-1){
                resultStr += s;
            }else{
                if(s != ','){
                    resultStr +=(s + ',')
                }
                else{
                    resultStr += s;
                }
            }
        }
    }
    return resultStr;
}
//플로리스트 관리자 링크 설정
function managerUrl(sid){
    $("#myStore").attr("href", "/manager/myStore?sid="+sid);
    $("#productList").attr("href", "/manager/productList?sid="+sid);
    $("#couponList").attr("href", "/manager/couponList?sid="+sid);
    $("#reviewList").attr("href", "/manager/reviewList?sid="+sid);

}

// 두 지점 간 거리를 구하는 함수
function getDistanceFromLatLonInKm(curLan, curLng, deslat, deslng) {
    var lat1 = curLan;
    var lng1 = curLng;
    var lat2 = deslat;
    var lng2 = deslng;

    function deg2rad(deg) {
        return deg * (Math.PI/180)
    }
    var R = 6371; // Radius of the earth in km
    var dLat = deg2rad(lat2-lat1);  // deg2rad below
    var dLon = deg2rad(lng2-lng1);
    var a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.sin(dLon/2) * Math.sin(dLon/2);
    var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
    var d = R * c; // Distance in km
    return d;
}


// -------------------------------

//            API Function

// -------------------------------

// 공지사항 리스트
function noticeList(){
    var data = {};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/main/getNoticeList",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);
            var noticeHtml = '';
            var MarketnoticeHtml = '';

            var index = 0;
            $(result.BODY).each(function(idx, notice) {

                index += 1;

                var title = "";
                var content = "";

                if(`${notice.title}`.length >= 20){
                    title = `${notice.title}`.substr(0,14)+"...";
                }
                else{
                    title = `${notice.title}`;
                }
                if(`${notice.contents}`.length >= 40){
                    content = `${notice.contents}`.substr(0,14)+"...";
                }
                else{
                    content = `${notice.contents}`;
                }


                var regdate = `${notice.regDate}`.substr(0,10);
                noticeHtml += `
                           <div class="item">
                               <a href="/notice/noticeDetail?nid=${notice.notice}">
                                    <p class="tit">`+ title +`</p>
                                    <span class="regdate">`+regdate+`</span>
                                    <p>`+content+`</p>
                                </a>
                            </div>

                                `;

                if(index <= 2){
                    MarketnoticeHtml += `
                           <li>
                                <a href="/notice/noticeDetail?nid=${notice.notice}">
                                    <div class="item-wrap">
                                        <div class="tit-wrap">
                                            ${notice.title}
                                        </div>
                                        <div class="date-wrap">
                                            `+regdate+`
                                        </div>
                                    </div>
                                </a>
                            </li>

                                `;
                }
            });
            $("#notice-list").html(noticeHtml);
            $("#marketNotice").html(MarketnoticeHtml);


        },
        error: function(result) {
            console.log(result);
        }
    });
}
// 공지사항 상세정보
function getNotice(nid){

    var data = {
        "notice" : nid
    };
    $.ajax({
        type: "POST",        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/main/getNotice",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);

            var regdate = result.BODY.regDate.substr(0,10);

            $('.title').text(result.BODY.title);
            $('.regdate').text(regdate);
            $('.detail-body p').text(result.BODY.contents);

        },
        error: function(result) {
            console.log(result);
        }
    });
}
// 메인 카테고리 리스트
function mainCategoryList(){
    var data = {};
    var index = 0;
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/main/getCategoryList",
        async:false,
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);
            var categoryHtml = '';

            $(result.BODY).each(function(idx, category) {

                if(idx == 0){
                    firstCategory = category.category;
                }


                index += 1;

                categoryHtml += `
                             <li class="swiper-slide">
                                <a href="">
                                    <span>${category.name}</span>    
                                </a>
                            </li>
                           
                                `;
            });
            $("#categoryList").html(categoryHtml);

        },
        error: function(result) {
            console.log(result);
        }
    });
    if(index <= 4){
        categorySlide = new Swiper('.category-wrap', {
            slidesPerView: 4,
            spaceBetween: 10,
            observer: true,
            observeParents: true,
            breakpoints: {
                370:{
                    slidesPerColumn: 3,
                },
            }
        });
    } else{
        categorySlide = new Swiper('.category-wrap', {
            slidesPerView: 4,
            slidesPerColumn: 2,
            spaceBetween: 10,
            observer: true,
            observeParents: true,
            breakpoints: {
                370:{
                    slidesPerColumn: 3,
                    slidesPerView: 2,
                },
            }
        });
    }
}

// 햔재 위치 근처 시장처
function getMarketInfoWithLatLon(lat, lon){

    var data = {
                    "lat" : lat,
                    "lon" : lon
                };
    $.ajax({
        type: "POST",        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/main/getMarketInfoWithLatLon",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);

            // $('.go-market').text(result.BODY.name + " 시장");
            $('.go-market').attr("href", "/market/marketDetail?mkid="+result.BODY.market );
            $('.go-event').attr("href", "/event/eventList?mkid="+result.BODY.market);


            //추천가게 리스트
            mainRecStoreList(result.BODY.market);
            //인기가게 리스트
            mainPopStoreList( result.BODY.market);

            getEnterMarketList('', 0, lat, lon)


        },
        error: function(result) {
            console.log(result);
        }
    });
}
// 메인 입점시장 리스트
function getEnterMarketList(keyword, order, lat, lon){

    var data = {

        "keyword" : keyword,
        "orderType" : order,
        "lat" : lat,
        "lon" : lon

    };

    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/main/getMarketListBySearch",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);
            var storeHtml = '';
            var index = 0;
            $(result.BODY).each(function(idx, market) {

                index += 1;

                var src = './resource/image/common/noimg.png';
                if(`${market.thumb}` != null && `${market.thumb}` != 'null' && `${market.thumb}` != undefined && `${market.thumb}` != "undefined" ){
                        src = ServerUrl+'/file/download?fileName='+`${market.thumb}`;
                }


                // if(index <= 6){
                //     storeHtml += `
                //                 <li class="swiper-slide" onclick="location.href='/market/marketDetail?mkid=${market.market}'">
                //                    <div class="market-item"style="background: url(`+src+`)">
                //                     </div>
                //                     <p class="market-name">${market.name}</p>
                //                  </li>
                //
                //                 `;
                // }

                if(`${market.market}` == 1 || `${market.market}` == 2 || `${market.market}` == 3 || `${market.market}` == 4 || `${market.market}` == 5 || `${market.market}` == 6){
                    storeHtml += `
                                <div class="swiper-slide" onclick="location.href='/market/searchCategory?mid=${market.market}'">
                                   <div class="market-item"style="background: url(`+src+`)">
                                    </div>
                                 </div>
                           
                                `;
            }


            });
            $("#marketSlide").html(storeHtml);

            marketSlide = new Swiper('.market-slide-container', {
                slidesPerView: 3,
                initialSlide: 0,
                spaceBetween: 15,
                grabCursor: true,
                loop: true,
                autoplay: {
                    delay: 2000,
                },
                observer: true,
                observeParents: true,
                navigation : { // 네비게이션 설정
                    nextEl : '.market-next', // 다음 버튼 클래스명
                    prevEl : '.market-prev', // 이번 버튼 클래스명
                },
                breakpoints: {
                    370:{
                        slidesPerColumn: 2,
                    },
                }
            });

        },
        error: function(result) {
            console.log(result);
        }
    });
}
// 근처 위치
function getMarketListBySearch(keyword, order, lat, lon){

    var data = {

        "keyword" : keyword,
        "orderType" : order,
        "lat" : lat,
        "lon" : lon

    };

    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/main/getMarketListBySearch",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);
            var storeHtml = '';

            $(result.BODY).each(function(idx, market) {



                var src = '/resource/image/common/noimg.png';
                if(`${market.thumb}` != null && `${market.thumb}` != 'null' && `${market.thumb}` != undefined && `${market.thumb}` != "undefined" ){
                    src = ServerUrl+'/file/download?fileName='+`${market.thumb}`;
                }


                var distance = getDistanceFromLatLonInKm(lat, lon, `${market.lat}`, `${market.lon}`)


                if (isNaN(distance)) { // 값이 없어서 NaN값이 나올 경우
                    distance = '';
                }
                else{
                    if(distance > 0){
                        distance = distance.toFixed(0) + "km";
                    }
                    else{
                        distance = " 1Km내에 있습니다. ";
                    }

                }


                var event = ''

                if(`${market.ecnt}` == 0){
                    event = "진행중 이벤트가 없습니다."
                }
                else{
                    event = "진행중 이벤트가 "+`${market.ecnt}`+"건 있습니다."
                }



                storeHtml += `
                                <div class="list-item" onclick="location.href='/market/marketDetail?mkid=${market.market}'">
                                    <div class="top-wrap">
                                        <div class="img-wrap">
                                            <a href="/market/marketDetail?mkid=${market.market}">
                                                <img src="`+src+`" width="71" height="71">
                                            </a>
                                        </div>
                                        <div class="info-wrap">
                                           <p class="market-name">${market.name}</p>
                                            <p class="market-text"><img src="../resource/image/common/location-img.png">${market.address}</p>
                                            <p class="distance">(`+distance+`)</p>
                                        </div>
                                    </div>
                                    <p class="market-info">`+event+`</p>
                                </div>            
                                `;
            });
            $("#marketList").html(storeHtml);

        },
        error: function(result) {
            console.log(result);
        }
    });
}
// 카테고리로 가게 찾기
function getStoreListByCategory( mid, keyword){

    var data = {

        "market" : mid,
        "keyword" : keyword

    };

    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/main/getStoreListByCategory",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);
            var storeHtml = '';

            $(result.BODY).each(function(idx, store) {

                var sInfo = '';
                var isOpen = '';
                var sthm = `${store.sthm}`;
                var ndhm = `${store.ndhm}`;

                var isOpenClass = '';
                isOpen = isSopen(sthm, ndhm);
                if(isOpen == '영업중'){
                    isOpenClass = 'open';
                }
                else{
                    isOpenClass = 'close';
                }
                if(`${store.sinfo}`.length >= 65){
                    sInfo = `${store.sinfo}`.substr(0,65)+"...";
                } else{
                    sInfo = `${store.sinfo}`;
                }

                var src = '../resource/image/store/temp.jpeg';
                // if(store.files.length != 0){
                //     if(store.files[0].type != 'L'){
                //         src = ServerUrl+'/file/download?fileName='+store.files[0].path;
                //     } else {
                //         if(store.files.length != 1){
                //             src = ServerUrl+'/file/download?fileName='+store.files[1].path;
                //         }
                //     }
                // }

                reviewAvg = parseFloat(`${store.avg}`).toFixed(1);

                storeHtml += `
                                <div class="list-item">
                                    <div class="top-wrap">
                                        <div class="img-wrap">
                                            <a href="/store/info?sid=${store.store}">
                                                <img src="`+src+`" width="98" height="98">
                                            </a>
                                        </div>
                                        <div class="info-wrap">
                                            <p class="market-name">
                                                <a href="/store/info?sid=${store.store}">
                                                    ${store.sname}
                                                </a>
                                            </p>
                                            <span class="is-open `+isOpenClass+`">`+isOpen+`</span>
                                            <span class="is-coupon">쿠폰</span>
                                            <p class="rate-text"><img src="../resource/image/manager/rate-icon.png"><span class="rate">`+reviewAvg+`</span> (<span class="review">${store.tot}</span>)</p>
                                            <p class="market-text"><img src="../resource/image/common/location-img.png"><span>${store.mname}</span></p>
                                        </div>
                                    </div>
                                    <p class="market-info">`+sInfo+`</p>
                                </div>            
                                `;
            });
            $("#storeList").html(storeHtml);

        },
        error: function(result) {
            console.log(result);
        }
    });
}
// 메인 카테고리 리스트
function mainCategoryList(){
    var data = {};
    var index = 0;
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/main/getCategoryList",
        async:false,
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);
            var categoryHtml = '';

            $(result.BODY).each(function(idx, category) {

                if(idx == 0){
                    firstCategory = category.category;
                }


                index += 1;

                categoryHtml += `
                             <li class="swiper-slide">
                                <a href="/market/searchCategory?category=${category.category}">
                                    <span>${category.name}</span>    
                                </a>
                            </li>
                           
                                `;
            });
            $("#categoryList").html(categoryHtml);

        },
        error: function(result) {
            console.log(result);
        }

    });
    if(index <= 4){
        categorySlide = new Swiper('.category-wrap', {
            slidesPerView: 4,
            spaceBetween: 10,
            observer: true,
            observeParents: true,
            breakpoints: {
                370:{
                    slidesPerColumn: 3,
                },
            }
        });
    } else{
        categorySlide = new Swiper('.category-wrap', {
            slidesPerView: 4,
            slidesPerColumn: 2,
            spaceBetween: 10,
            observer: true,
            observeParents: true,
            breakpoints: {
                370:{
                    slidesPerColumn: 3,
                    slidesPerView: 2,
                },
            }
        });
    }
}
// 메인 추천 가게 리스트
function mainRecStoreList(mid){

    var data = {

                "market":mid

                };

    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/main/getRecStoreList",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);
            var recStoreHtml = '';

            $(result.BODY).each(function(idx, recStore) {

                 var sInfo = '';
                 var isOpen = '';
                 var sthm = `${recStore.sthm}`;
                 var ndhm = `${recStore.ndhm}`;

                 var isOpenClass = '';
                 isOpen = isSopen(sthm, ndhm);
                 if(isOpen == '영업중'){
                    isOpenClass = 'open';
                 }
                 else{
                    isOpenClass = 'close';
                 }
                 if(`${recStore.sinfo}`.length >= 65){
                    sInfo = `${recStore.sinfo}`.substr(0,65)+"...";
                } else{
                    sInfo = `${recStore.sinfo}`;
                }

                 var src = 'https://via.placeholder.com/98x98';
                 if(recStore.files.length != 0){
                     if(recStore.files[0].type != 'L'){
                         src = ServerUrl+'/file/download?fileName='+recStore.files[0].path;
                     } else {
                         if(recStore.files.length != 1){
                             src = ServerUrl+'/file/download?fileName='+recStore.files[1].path;
                         }
                     }
                 }

                 reviewAvg = parseFloat(`${recStore.avg}`).toFixed(1);

                 recStoreHtml += `
                                <div class="list-item">
                                    <div class="top-wrap">
                                        <div class="img-wrap">
                                            <a href="/store/info?sid=${recStore.store}">
                                                <img src="`+src+`" width="98" height="98">
                                            </a>
                                        </div>
                                        <div class="info-wrap">
                                            <p class="market-name">
                                                <a href="/store/info?sid=${recStore.store}">
                                                    ${recStore.sname}
                                                </a>
                                            </p>
                                            <span class="is-open `+isOpenClass+`">`+isOpen+`</span>
                                            <span class="is-coupon">쿠폰</span>
                                            <p class="rate-text"><img src="./resource/image/manager/rate-icon.png"><span class="rate">`+reviewAvg+`</span> (<span class="review">${recStore.tot}</span>)</p>
                                            <p class="market-text"><img src="./resource/image/common/location-img.png"><span>`+marketName+`</span></p>
                                        </div>
                                    </div>
                                    <p class="market-info">`+sInfo+`</p>
                                </div>            
                                `;
            });
            $("#recStore").html(recStoreHtml);

        },
        error: function(result) {
            console.log(result);
        }
    });
}
// 메인 인기 가게 리스트
function mainPopStoreList( mid){
    var data = {
        "market":mid

    };
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/main/getPopStoreList",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);
            var popStoreHtml = '';

            $(result.BODY).each(function(idx, popStore) {

                var sInfo = '';
                var isOpen = '';
                var sthm = `${popStore.sthm}`;
                var ndhm = `${popStore.ndhm}`;
                isOpen = isSopen(sthm, ndhm);
                var isOpenClass = '';
                isOpen = isSopen(sthm, ndhm);
                if(isOpen == '영업중'){
                   isOpenClass = 'open';
                }
                else{
                   isOpenClass = 'close';
                }
                if(`${popStore.sinfo}`.length >= 65){
                    sInfo = `${popStore.sinfo}`.substr(0,65)+"...";
                } else{
                    sInfo = `${popStore.sinfo}`;
                }

                var src = 'https://via.placeholder.com/98x98';
                if(popStore.files.length != 0){
                    if(popStore.files[0].type != 'L'){
                        src = ServerUrl+'/file/download?fileName='+popStore.files[0].path;
                    } else {
                        if(popStore.files.length != 1){
                            src = ServerUrl+'/file/download?fileName='+popStore.files[1].path;
                        }
                    }
                }

                reviewAvg = parseFloat(`${popStore.avg}`).toFixed(1);

                popStoreHtml += `
                                <div class="list-item">
                                    <div class="top-wrap">
                                        <div class="img-wrap">
                                            <a href="/store/info?sid=${popStore.store}">
                                                <img src="`+src+`" width="98" height="98">
                                            </a>
                                        </div>
                                        <div class="info-wrap">
                                            <p class="market-name">
                                                <a href="/store/info?sid=${popStore.store}">
                                                    ${popStore.sname}
                                                </a>
                                            </p>
                                            <span class="is-open `+isOpenClass+`">`+isOpen+`</span>
                                            <span class="is-coupon">쿠폰</span>
                                            <p class="rate-text"><img src="./resource/image/manager/rate-icon.png"><span class="rate">`+reviewAvg+`</span> (<span class="review">${popStore.tot}</span>)</p>
                                            <p class="market-text"><img src="./resource/image/common/location-img.png"><span>`+marketName+`</span></p>
                                        </div>
                                    </div>
                                    <p class="market-info">`+sInfo+`</p>
                                </div>            
                                `;
            });
            $("#popStore").html(popStoreHtml);

        },
        error: function(result) {
            console.log(result);
        }
    });
}
// // 메인 가게 리스트
// function storeList(categoryId, mid){
//     var data = {
//         "category":categoryId,
//         "market":mid
//
//     };
//     $.ajax({
//         type: "POST",
//         headers: {
//             "Content-Type": "application/json",
//             "X-HTTP-Method-Override": "POST"
//         },
//         url: ServerUrl+"/main/getStoreList",
//         data: JSON.stringify(data),
//         success: function(result) {
//             console.log(result);
//             var storeHtml = '';
//
//             $(result.BODY).each(function(idx, store) {
//
//                 var sInfo = '';
//                 var isOpen = '';
//                 var sthm = `${store.sthm}`;
//                 var ndhm = `${store.ndhm}`;
//                 isOpen = isSopen(sthm, ndhm);
//                 var isOpenClass = '';
//                 isOpen = isSopen(sthm, ndhm);
//                 if(isOpen == '영업중'){
//                    isOpenClass = 'open';
//                 }
//                 else{
//                    isOpenClass = 'close';
//                 }
//                 if(`${store.sinfo}`.length >= 65){
//                     sInfo = `${store.sinfo}`.substr(0,65)+"...";
//                 } else{
//                     sInfo = `${store.sinfo}`;
//                 }
//
//                 var src = 'https://via.placeholder.com/98x98';
//                 if(store.files.length != 0){
//                     if(store.files[0].type != 'L'){
//                         src = ServerUrl+'/file/download?fileName='+store.files[0].path;
//                     } else {
//                         if(store.files.length != 1){
//                             src = ServerUrl+'/file/download?fileName='+store.files[1].path;
//                         }
//                     }
//                 }
//
//                 storeHtml += `
//                                 <div class="list-item">
//                                     <div class="img-wrap">
//                                         <a href="/main/info?sid=${store.store}">
//                                             <img src="`+src+`" width="98" height="98">
//                                         </a>
//                                     </div>
//                                     <div class="info-wrap">
//                                         <p class="market-name">
//                                             <a href="/main/info?sid=${store.store}">
//                                                 ${store.sname}
//                                             </a>
//                                         </p>
//                                         <p class="market-info">`+sInfo+`</p>
//                                         <div class="info-bottom-wrap">
//                                             <div class="btn-wrap">
//                                                 <a href="#">
//                                                     <img src="resource/image/main/coupon-icon.png" />
//                                                 </a>
//                                             </div>
//                                             <div class="btn-wrap">
//                                                 <a href="tel:${store.sphone}">
//                                                     <img src="resource/image/main/phone-icon.png" />
//                                                 </a>
//                                             </div>
//                                             <div class="btn-wrap">
//                                                 <a href="#">
//                                                     <i class="fas fa-headset"></i>
//                                                 </a>
//                                             </div>
//                                             <div class="more-wrap">
//                                                 <a href="/main/info?sid=${store.store}">더보기 ></a>
//                                             </div>
//                                             <span class="market-status `+isOpenClass+`">`+isOpen+`</span>
//                                         </div>
//                                     </div>
//                                 </div>
//                                 `;
//                 $("#storeList").html(storeHtml);
//             });
//         },
//         error: function(result) {
//             console.log(result);
//         }
//     });
// }

// 시장 상세정보 불러오기 (사용자)
function getMarketDetail(mkid){
    var data = {"market":mkid};
    var num = 0;
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/main/getMarket",
        data: JSON.stringify(data),
        success: function(result) {

            var categoryHtml = '';


            var imgHtml = '';
            if(result.BODY.files.length != 0){
                $(result.BODY.files).each(function(idx, file) {
                    var src = ServerUrl+'/file/download?fileName='+`${file.path}`;
                    var mrfile = `${file.mrfile}`;
                    imgHtml += '   <img class="preview" src="'+src+'" style="display: block;"/>';
                });
            }

            $("#imgList").html(imgHtml);



            $(result.BODY.categories).each(function(idx, category) {
                num += 1;
                categoryHtml += `
                             <li class="swiper-slide">
                                <a href="/market/searchCategory?category=${category.category}&mid=`+ mkid +`">
                                    <span>${category.name}</span>    
                                </a>
                            </li>
                           
                                `;
            });
            $("#categoryList").html(categoryHtml);


            $('.market-name').text(result.BODY.name);

            $('.go-market').text(result.BODY.name );
            $('.go-market').attr("href", "/market/marketDetail?mkid="+result.BODY.market );
            $('.go-event').attr("href", "/event/eventList?mkid="+result.BODY.market);

            $('.market-info-text').text(result.BODY.detail);
            $('.phone').text(result.BODY.phone);
            $('.address').text(result.BODY.address);
            $('.holiday').text(result.BODY.holiday);
            $('.homepage').text(result.BODY.homepage);
            $('.open-time').text(result.BODY.open);
            $('.park').text(result.BODY.park);

            categorySlide = new Swiper('.category-wrap', {
                slidesPerView: 4,
                slidesPerColumn: 2,
                spaceBetween: 10,
                observer: true,
                observeParents: true,
                scrollbar : {
                    el : '.swiper-scrollbar',
                    draggable: true,
                },
                breakpoints: {
                    370:{
                        slidesPerColumn: 3,
                        slidesPerView: 2,
                    },
                }
            });
        },
        error: function(result) {
            console.log(result);
        }
    });



}
//  이벤트 시장 불러오기 (사용자)
function getEventMarket(mkid){
    var index = 0;
    var data = {"market":mkid};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/main/getMarket",
        data: JSON.stringify(data),
        success: function(result) {

            var categoryHtml = '';

            $(result.BODY.categories).each(function(idx, category) {
                index += 1;
                categoryHtml += `
                             <li class="swiper-slide">
                                <a href="/market/searchCategory?category=${category.category}">
                                    <span>${category.name}</span>    
                                </a>
                            </li>
                           
                                `;

            });
            $("#categoryList").html(categoryHtml);


            $('.market-name').text(result.BODY.name);

            $('.go-market').text(result.BODY.name + " 시장");
            $('.go-market').attr("href", "/market/marketDetail?mkid="+result.BODY.market );
            $('.go-event').attr("href", "/event/eventList?mkid="+result.BODY.market );


        },
        error: function(result) {
            console.log(result);
        }
    });

    if(index <= 4){
        categorySlide = new Swiper('.category-wrap', {
            slidesPerView: 4,
            spaceBetween: 10,
            observer: true,
            observeParents: true,
            breakpoints: {
                370:{
                    slidesPerColumn: 3,
                },
            }
        });
    } else{
        categorySlide = new Swiper('.category-wrap', {
            slidesPerView: 4,
            slidesPerColumn: 2,
            spaceBetween: 10,
            observer: true,
            observeParents: true,
            breakpoints: {
                370:{
                    slidesPerColumn: 3,
                    slidesPerView: 2,
                },
            }
        });
    }
}
// 이벤트 리스트 불러오기(사용자)
function getEventList(mkid, order){
    var index = 0;
    var data = {"market":mkid, "orderType" : order};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/main/getEventListForMain",
        data: JSON.stringify(data),
        success: function(result) {

            var mname = '';
            var eventHtml = '';
            var isProce = '';
            var proceStatus = '';


            $(result.BODY).each(function(idx, event) {

                mname = `${event.mname}`;

                var today = new Date();
                var year = `${event.nddt}`.substr(0,4);
                var month = `${event.nddt}`.substr(5,2);
                var date = `${event.nddt}`.substr(8,2);

                var dday = new Date(year, month, date);

                if(eval(today) >= eval(dday)){
                    isProce = '마감';
                    proceStatus = 'close'
                }
                else{
                    isProce = '진행중';
                    proceStatus = 'open'
                }

                var imgHtml = '';
                var src;

                console.log(result.BODY[idx]);
                if(`${event.files}` != null || `${event.files}` != 'null' || `${event.files}` != undefined || `${event.files}` != "undefined" ){

                    if(`${event.files}`.length != 0){

                        console.log(result.BODY[idx]);

                        src = ServerUrl+'/file/download?fileName='+`${event.files[0].path}`;
                        // imgHtml +=
                        //     `<li className="swiper-slide">\n`+
                        //     `  <img src="`+src+`">\n`+
                        //     `</li>`;
                    }
                    // $("#eventSlide").html(imgHtml);
                }
                else{
                    src = 'https://via.placeholder.com/320x180';
                }



                eventHtml += `
                         <div class="item" onclick="location.href='/event/eventDetail?eid=${event.event}'">
                            <img class="event-img" src="`+src+`">
                            <p class="event-name">${event.title}</p>
                            <p class="market-name"><img src="../resource/image/common/location-img.png"><span>${event.mname}</span></p>
                            <p class="date"><span>`+ `${event.bgndt}`.substr(0,10).replace(/-/gi,".")+`</span> ~ <span>`+ `${event.nddt}`.substr(0,10).replace(/-/gi,".")+`</span></p>
                            <span class="status `+proceStatus+`">`+isProce+`</span>
                        </div>
                            `;
            });
            $("#eventList").html(eventHtml);


            // $('.body-wrapper p').text(mname);

            // $('.go-market').text(result.BODY.market + " 시장");
            // $('.go-event').attr("href", "/market/marketDetail?mkid="+result.BODY.market );



        },
        error: function(result) {
            console.log(result);
        }
    });
}
// 이벤트 슬라이드 불러오기(사용자)
function getEventSlide(){
    var index = 0;
    var data = {};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/main/getEventList",
        data: JSON.stringify(data),
        success: function(result) {

            var imgHtml = '';
            var src;

            $(result.BODY).each(function(idx, event) {
                index += 1;

                var today = new Date();
                var year = `${event.nddt}`.substr(0,4);
                var month = `${event.nddt}`.substr(5,2);
                var date = `${event.nddt}`.substr(8,2);

                var dday = new Date(year, month, date);

                if(eval(today) >= eval(dday)){
                    isProce = '마감';
                    proceStatus = 'close'
                }
                else{
                    isProce = '진행중';
                    proceStatus = 'open'
                }


                if(`${event.files}` != null || `${event.files}` != 'null' || `${event.files}` != undefined || `${event.files}` != "undefined" ){

                    if(`${event.files}`.length != 0){

                        src = ServerUrl+'/file/download?fileName='+`${event.files[0].path}`;
                        imgHtml +=
                            `<li class="swiper-slide">\n`+
                            ` <span class="status `+ proceStatus +`"> `+ isProce +`</span>\n`+
                            `  <img src="`+src+`" onclick="location.href='/event/eventDetail?eid=${event.event}'">\n`+
                            `</li>`;
                    }
                }


                if(index == 1){
                    $('.event-link').attr('href', '/event/eventDetail?eid='+`${event.event}`);
                    $('.event-name').text(`${event.title}`);
                    $('.mname').text(`${event.mname}`);
                    $('.event-img').attr("src",src);
                    $('.bgndt').text(`${event.bgndt}`.substr(0,10).replace(/-/gi,"."));
                    $('.nddt').text(`${event.nddt}`.substr(0,10).replace(/-/gi,"."));
                }

            });

            banneerList('T', imgHtml);

            if(index == 0){
                $('.event-wrap').hide();
                $('.no-event').show();
            }

        },
        error: function(result) {
            console.log(result);
        }
    });

    // Main Slide Banner
    var slide = new Swiper('.slide-banner-wrap', {
        slidesPerView : '1',
        pagination : {
            el : '.swiper-pagination',
            clickable : true,
        },
        autoplay: {
            delay: 2000,
            disableOnInteraction: true // 쓸어 넘기거나 버튼 클릭 시 자동 슬라이드 정지.
        },
        loop: true
    });
}
// 메인 이벤트 리스트 불러오기(사용자)
function getMainEventList(){
    var index = 0;
    var data = {};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/main/getEventList",
        data: JSON.stringify(data),
        success: function(result) {

            var imgHtml = '';
            var src;

            $(result.BODY).each(function(idx, event) {
                index += 1;

                if(`${event.files}` != null || `${event.files}` != 'null' || `${event.files}` != undefined || `${event.files}` != "undefined" ){

                    if(`${event.files}`.length != 0){

                        src = ServerUrl+'/file/download?fileName='+`${event.files[0].path}`;
                        imgHtml +=
                            `  <img src="`+src+`" onclick="location.href='/event/eventDetail?eid=${event.event}'">\n`
                    }
                }
            });
            $("#eventList").html(imgHtml);

        },
        error: function(result) {
            console.log(result);
        }
    });

}
// 이벤트 정보 불러오기
function getEvent(eid){
    // 가게정보
    var data = {"event":eid};

    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        async:false,
        url: ServerUrl+"/main/getEvent",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);

            $('.tit').text(result.BODY.title);
            $('.mname').text(result.BODY.mname);
            $('.content').text(result.BODY.contents);
            $('.bgndt').text(result.BODY.title.bgndt.substr(0,10).replace(/-/gi,"."));
            $('.nddt').text(result.BODY.title.nddt.substr(0,10).replace(/-/gi,"."));
        },
        error: function(result) {
            console.log(result);

        }
    });
}
// 이벤트 신청하기
function registerEvent(eid, mid){
    // 가게정보
    var data = {"event":eid, "member":mid};

    var userId;
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        async:false,
        url: ServerUrl+"/user/registerEvent",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);

            alert('신청이 완료되었습니다.');
        },
        error: function(result) {
            console.log(result);

        }
    });
}
//가게 상세정보 (사용자)
function storeInfoUser(sid){
    // 가게정보
    var data = {"store":sid};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        async:false,
        url: ServerUrl+"/main/getStore",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);

            var sthm = result.BODY.sthm;
            var ndhm = result.BODY.ndhm;
            isOpen = isSopen(sthm, ndhm);
            var isOpenClass = '';
            isOpen = isSopen(sthm, ndhm);
            if(isOpen == '영업중'){
               isOpenClass = 'open';
            }
            else{
               isOpenClass = 'close';
            }
            address = result.BODY.address;
            title = result.BODY.sname;
            desc = result.BODY.sinfo;

            $('.mname').text(result.BODY.mname);
            $('.is-open').text(isOpen);
            $('.is-open').addClass(isOpenClass);

            var openday = '';
            $(result.BODY.workdays).each(function(idx, work) {
                openday += openDay(`${work.type}`);
            });
            $('.openday').text(addComma(openday));
            var categoryType = '';
            $(result.BODY.categories).each(function(idx, category) {
                categoryType += `${category.name}` + ', ';
            });
            $('.categoryType').text(categoryType.substr(0,categoryType.length-2));
            $('.market-name').text(result.BODY.sname);
            $('.sthm').text(sthm.substr(0,5));
            $('.ndhm').text(ndhm.substr(0,5));
            $('.holiday').text(result.BODY.holidayInfo);
            $('.sphone').prop('href', 'tel:'+result.BODY.sphone);
            $('.phone').text(result.BODY.sphone);
            $('.kakao').prop('href', result.BODY.kakao);
            $('.sinfo').text(result.BODY.sinfo);
            $('.address').text(result.BODY.address);

            var imgHtml = '';
            if(result.BODY.files.length != 0){
                if(result.BODY.files.length == 1 && result.BODY.files[0].type == 'L'){
                    imgHtml += '' +
                        '<li class="swiper-slide">\n' +
                        '    <img src="../resource/image/store/banner_temp.png">\n' +
                        '</li>';
                    $("#imgList").html(imgHtml);
                } else {
                    $(result.BODY.files).each(function(idx, file) {
                        if(`${file.type}` == 'L') {
                            var src = ServerUrl+'/file/download?fileName='+`${file.path}`;
                            var loImgHtml = '<img src="'+src+'" width="380" height="180" />';
                            $("#loImg").html(loImgHtml);
                        } else {
                            var src = ServerUrl+'/file/download?fileName='+`${file.path}`;

                            if(idx == 0){
                                shareImg = src;
                            }

                            imgHtml += '' +
                                '<li class="swiper-slide">\n' +
                                '    <img src="'+src+'" width="420" height="180">\n' +
                                '</li>';
                            $("#imgList").html(imgHtml);
                            // Main Slide Banner
                            var slide = new Swiper('.slide-banner-wrap', {
                                slidesPerView : '1',
                                pagination : {
                                    el : '.swiper-pagination',
                                    clickable : true,
                                },
                                autoplay: {
                                    delay: 2000,
                                    disableOnInteraction: true // 쓸어 넘기거나 버튼 클릭 시 자동 슬라이드 정지.
                                },
                                loop: true
                            });
                        }
                    });
                }
            } else {
                imgHtml += '' +
                    '<li class="swiper-slide">\n' +
                    '    <img src="../resource/image/store/banner_temp.png">\n' +
                    '</li>';
                $("#imgList").html(imgHtml);
            }
        },
        error: function(result) {
            console.log(result);

        }
    });
}
// 가게 메뉴정보 리스트 (사용자)
function getMenuListUser(sid){
    var data = {"store":sid};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/main/getProductList",
        data: JSON.stringify(data),
        success: function(result) {
            var menuHtml = '';
            $(result.BODY).each(function(idx, menu) {

                var src = 'https://via.placeholder.com/88x88';

                if(menu.files.length != 0){
                    src = ServerUrl+'/file/download?fileName='+menu.files[0].path;
                }

                menuHtml += `
                            <div class="menu-item">
                                <div class="menu-wrap">
                                    <div class="img-wrap">
                                        <img src="`+src+`" width="88" height="88">
                                    </div>
                                    <div class="info-wrap">
                                        <p class="menu-name">${menu.mname}</p>
                                        <p class="menu-price">${menu.price}원</p>
                                    </div>
                                </div>
                                <p class="menu-info">${menu.minfo}</p>
                            </div>
                            `;
            });
            $("#menuList").html(menuHtml);

        },
        error: function(result) {
            console.log(result);
        }
    });
}
// 가게 리뷰 리스트 (사용자)
function getReviewListUser(sid){
    var data = {"store":sid};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/main/getReviewList",
        data: JSON.stringify(data),
        success: function(result) {
            var reviewHtml = '';
            var total = 0;
            var sum = 0;
            var num = 0;
            var userId;
            $(result.BODY).each(function(idx, review) {

                var regdate = `${review.regDate}`.substr(0,10);
                sum += parseFloat(`${review.rate}`);
                num += 1;

                userId = `${review.member}`

                reviewHtml += `
                           <div class="item">
                                <div class="top-wrap">
                                    <div class="rate-wrap">
                                        <p><img src="../resource/image/store/rate-icon.png"> ${review.rate}</p>
                                    </div>
                                    <div class="user-info-wrap">
                                        <p class="id">`+reviewUserId(userId)+`</p>
                                        <p class="regdate">`+regdate+`</p>
                                    </div>
                                </div>
                                <div class="bottom-wrap">
                                    <p>${review.contents}</p>
                                </div>
                            </div>
                            `;
            });
            $("#reviewHtml").html(reviewHtml);

            total = sum/num;

            if (isNaN(total)) { // 값이 없어서 NaN값이 나올 경우

                total = 0;

            }

            $('.total-rate-wrap .total').text(total.toFixed(1));
            $('.total-rate-wrap .review-num').text(num+' (0 new)');

        },
        error: function(result) {
            console.log(result);
        }
    });
}


// 가게 쿠폰 리스트 (비로그인)
function getStoreCouponListMain(sid){
    var num ;
    var data = {"store":sid};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/main/getCouponList",
        data: JSON.stringify(data),
        success: function(result) {
            var couponHtml = '';
            $(result.BODY).each(function(idx, coupon) {
                style1 = '';
                style2 = '';
                num = idx;


                if(`${coupon.ctype}`=='1') {
                    stanprice = parseInt(`${coupon.stanprice}`).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + "이상 주문시";
                    disprice = parseInt(`${coupon.disprice}`).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + "원 할인";
                }
                else{
                    stanprice = "";
                    disprice = "배달비 무료";
                    style1 = "style='display:none;'";
                    style2 = "style='margin-top:0;'";
                }

                couponHtml += `
                           <div class="item">
                                <div class="left-wrap">
                                    <p class="stanprice" `+style1+`>`+stanprice+`</p>
                                    <p class="disprice" `+style2+'>'+disprice+`</p>
                                </div>
                                <div class="right-wrap">
                                    <p class="issued-coupon">쿠폰 받기</p>
                                </div>
                            </div>
                            `;
            });
            $("#couponList").html(couponHtml);


            if(num != undefined && num != "undefined" && num != null && num != ''){
                $(' .is-coupon').show();
            }
        },
        error: function(result) {
            console.log(result);
        }
    });

    $('.coupon-list-wrap').on('click', '.right-wrap' ,function(){
        alert('로그인 후 이용가능합니다.');
    });
}
// 가게 쿠폰 리스트 (로그인)
function getStoreCouponListUser(sid, mid){
    var num;

    var data = {
                    "store":sid,
                    "member" : mid
                };
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/main/getCouponList",
        data: JSON.stringify(data),
        success: function(result) {
            var couponHtml = '';
            var cno;
            $(result.BODY).each(function(idx, coupon) {
                style1 = '';
                style2 = '';

                num = idx;

                if(`${coupon.ctype}`=='1') {
                    stanprice = parseInt(`${coupon.stanprice}`).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + "이상 주문시";
                    disprice = parseInt(`${coupon.disprice}`).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + "원 할인";
                }
                else{
                    stanprice = "";
                    disprice = "배달비 무료";
                    style1 = "style='display:none;'";
                    style2 = "style='margin-top:0;'";
                }
                cno = `${coupon.cno}`;

                if(cno == null || cno == '' || cno == undefined || cno == "undefined" || cno == "null"){

                    couponHtml += `
                           <div class="item">
                                <div class="left-wrap">
                                    <p class="stanprice" `+style1+`>`+stanprice+`</p>
                                    <p class="disprice" `+style2+'>'+disprice+`</p>
                                </div>
                                <div class="right-wrap">
                                   <input type="hidden" name="cid" value="${coupon.coupon}"/> 
                                    <p class="">쿠폰받기</p>
                                </div>
                            </div>
                            `;
                }
                else{
                    couponHtml += `
                           <div class="item">
                                <input type="hidden" name="cid" value="${coupon.coupon}"/> 
                                <div class="left-wrap">
                                    <p class="stanprice" `+style1+`>`+stanprice+`</p>
                                    <p class="disprice" `+style2+'>'+disprice+`</p>
                                </div>
                                <div class="right-wrap already-save">
                                    <input type="hidden" name="cid" value="${coupon.coupon}"/> 
                                    <p class="">받기 완료</p>
                                </div>
                            </div>
                            `;
                }

            });
            $("#couponList").html(couponHtml);


            if(num == undefined || num == "undefined" || num == null || num == ''){
                $('.is-coupon').hide();
            }
            else{
                $('.is-coupon').show();
            }
        },
        error: function(result) {
            console.log(result);
        }
    });

    $('.coupon-list-wrap').on('click', '.right-wrap' ,function(){
        if(!$(this).hasClass('already-save')){
            var cid = $(this).find('input[name=cid]').val();
            $(this).addClass('already-save');
            $(this).find('p').text('받기 완료');
            $('.coupon-issued-suc').fadeIn(800);
            $('.coupon-issued-suc').fadeOut(800);
            issuedCoupon(mid, cid);

        }
    });
}
// 가게 쿠폰 발급받기
function issuedCoupon(mid, cid){
    var data = {
                    "member":mid,
                    "coupon":cid
                };
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/user/issuedCoupon",
        data: JSON.stringify(data),
        success: function(result) {

        },
        error: function(result) {
            console.log(result);
        }
    });
}
// 방문자수 카운트
function countUpVisitor(sid){
    var data = {
        "store": sid
        };
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/main/countUpVisitor",
        data: JSON.stringify(data),
        success: function(result) {
        },
        error: function(result) {
            console.log(result);
        }
    });
}


// 리뷰 회원아이디 (사용자)
function reviewUserId(mid){
    // 가게정보
    var data = {"member":mid};

    var userId;
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        async:false,
        url: ServerUrl+"/user/getUser",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);

            userId = result.BODY.id;

        },
        error: function(result) {
            console.log(result);

        }
    });
    return userId;
}
// 마이페이지 정보 불러오기
function getUser(mid){
    // 가게정보
    var data = {"member":mid};

    var userId;
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        async:false,
        url: ServerUrl+"/user/getUser",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);

            $('.userId').text(result.BODY.id);
            $('.name').text(result.BODY.name);
            $('.phone').text(result.BODY.phone);
            $('.address').text(result.BODY.address);
        },
        error: function(result) {
            console.log(result);

        }
    });
}
// 플로리스트 관리자 계정 정보 수정 불러오기
function getUserDetail(mid){
    var data = {"member" : mid};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/manager/getManager",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);

            $('#member').val(result.BODY.member);
            $('.id').val(result.BODY.id);
            $('.name').val(result.BODY.name);
            $('.phone').val(result.BODY.phone);
            $('.email').val(result.BODY.email);
            $('.address').val(result.BODY.address);
        },
        error: function(result) {
            console.log(result);

        }
    });
}


// 쿠폰 보유 리스트 (사용자)
function getCouponListUser(mid){
    var data = {"member":mid};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/user/getCouponList",
        data: JSON.stringify(data),
        success: function(result) {
            var couponHtml = '';
            $(result.BODY).each(function(idx, coupon) {

                var src = 'https://via.placeholder.com/88x88';

                if(coupon.files.length != 0){
                    if(coupon.files[0].type != 'L'){
                        src = ServerUrl+'/file/download?fileName='+coupon.files[0].path;
                    } else {
                        if(coupon.files.length != 1){
                            src = ServerUrl+'/file/download?fileName='+coupon.files[1].path;
                        }
                    }
                }

                //쿠폰 이름
                var cname;
                if(`${coupon.cname}`.length >= 15){
                    cname = `${coupon.cname}`.substr(0,15)+"...";
                } else {
                    cname = `${coupon.cname}`;
                }

                //쿠폰 설명
                var cinfo;
                if(`${coupon.cinfo}`.length >= 30){
                    cinfo = `${coupon.cinfo}`.substr(0,30)+"...";
                } else {
                    cinfo = `${coupon.cinfo}`;
                }

                //할인 텍스트
                var discount;
                if(`${coupon.ctype}`=='1'){
                    discount = parseInt(`${coupon.disprice}`).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + "원 할인";
                }
                else{
                    discount = "배달비 무료"
                }

                //남은일 계산
                var today = new Date();
                var year = `${coupon.nddt}`.substr(0,4);
                var month = `${coupon.nddt}`.substr(5,2);
                var date = `${coupon.nddt}`.substr(8,2);

                var dday = new Date(year, month, date);
                var gap = dday.getTime() - today.getTime();
                var result = Math.ceil(gap / (1000 * 60 * 60 * 24));



                couponHtml += `
                            <div class="coupon-item-wrap" onclick="location.href='/user/couponDetail?cid=${coupon.coupon}&sid=${coupon.store}'">
                                <div class="thumb-wrap">
                                    <img src="`+src+`">
                                </div>
                                <div class="info-wrap">
                                    <p class="market-name">`+cname+`</p>
                                    <p class="coupon-info">`+cinfo+`</p>
                                    <p class="sale-info">`+discount+`</p>
                                    <span class="deadline">`+result+`일 남음</span>
                                </div>
                            </div>
                            `;
            });
            $("#couponList").html(couponHtml);

        },
        error: function(result) {
            console.log(result);
        }
    });
}
// 쿠폰 상세정보 (사용자)
function getCouponDetailUser(cid, mid){
    var data = {
                    "coupon":cid,
                    "member":mid
                };
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl + "/user/getCoupon",
        data: JSON.stringify(data),
        success: function (result) {

            var src = 'https://via.placeholder.com/88x88';

            if(result.BODY.files.length != 0){
                if(result.BODY.files[0].type != 'L'){
                    src = ServerUrl+'/file/download?fileName='+result.BODY.files[0].path;
                } else {
                    if(result.BODY.files.length != 1){
                        src = ServerUrl+'/file/download?fileName='+result.BODY.files[1].path;
                    }
                }
            }

            //남은일 계산
            var today = new Date();
            var year = result.BODY.nddt.substr(0,4);
            var month = result.BODY.nddt.substr(5,2);
            var date = result.BODY.nddt.substr(8,2);

            var dday = new Date(year, month, date);
            var gap = dday.getTime() - today.getTime();
            var Dresult = Math.ceil(gap / (1000 * 60 * 60 * 24));

            //할인 텍스트
            var discount;
            if(result.BODY.ctype=='1'){
                discount = parseInt(result.BODY.disprice).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + "원 할인";
            }
            else{
                discount = "배달비 무료"
            }
            $('.thumb-wrap img').attr('src', src);
            $('.sale-info').text(discount);
            $('.coupon-info').text(result.BODY.cinfo);
            $('#coupon-num').text(result.BODY.cno);
            $('.deadline').text(Dresult+"일 남음");
            $('#notice-text').text(result.BODY.cnote);
        },
        error: function(result) {
            console.log(result);
        }
    });
}
// 쿠폰 가게이름정보 (사용자)
function couponStoreName(sid){
    // 가게정보
    var data = {"store":sid};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        async:false,
        url: ServerUrl+"/main/getStore",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);

            $('.market-name').text(result.BODY.sname);

        },
        error: function(result) {
            console.log(result);

        }
    });
}

// 가게 상세정보(관리자) 카테고리 리스트
function managerCategoryList(mid){
    var data = {};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/main/getCategoryList",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);
            var categoryHtml = '';

            $(result.BODY).each(function(idx, category) {

                categoryHtml += `
                                    <option value="${category.category}">${category.name}</option>
                                `;
            });
            $("#category").html(categoryHtml);


        },
        error: function(result) {
            console.log(result);
        }
    });
}
// 가게 상세정보(관리자) 시장 리스트
function managerMarketList(){
    var data = {};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/manager/getMarketList",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);
            var marketHtml = '';
            var categoryhtml = '';


            marketHtml += `  <option value="">선택하세요</option>  `;
            $(result.BODY).each(function(idx, market) {

                marketHtml += `
                                    <option value="${market.market}">${market.name}</option>
                                `;

            });
            $("#market").html(marketHtml);

        },
        error: function(result) {
            console.log(result);
        }
    });
}
// 가게 등록 카테고리 리스트
function managerMarketCategory(mid, val) {
    var data = {"market" : mid};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/manager/getMarket",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);
            var categoryhtml = '';

            //카테고리
            $(result.BODY.categories).each(function(idx, category) {

                categoryhtml += `
                                <option value="${category.category}">${category.name}</option>
                            `;
            });
            $("#category").html(categoryhtml);


            $("#category").val(val).prop("selected", true);

        },
        error: function(result) {
            console.log(result);
        }
    });
}
//가게 상세정보 (관리자)
function managerStoreInfo(sid){
    // 가게정보
    var data = {"store":sid};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/manager/getStore",
        data: JSON.stringify(data),
        success: function(result) {
            var categoryhtml = '';
            console.log(result);

            //내가게 축약정보
            $('.store-name').text(result.BODY.sname);


            $(result.BODY.categories).each(function(idx, category) {
                managerMarketCategory(result.BODY.market, `${category.category}`);

                $('.category').text(`${category.name}`);
            });



            var sInfo;
            if(result.BODY.sinfo.length >= 30){
                sInfo = result.BODY.sinfo.substr(0,30)+"...";
            } else {
                sInfo = result.BODY.sinfo;
            }
            $('.store-info').text(sInfo);
            // $('.store-info-wrap').click(function(){
            //     $(location).attr('href', '/manager/editStore?cmd=2&sid='+sid);
            // });
            //-------------------------------------------------------------//



            // 가게 상세 입렵정보
            $('input[name=store]').val(result.BODY.store);
            $('input[name=sname]').val(result.BODY.sname);
            $('input[name=mname]').val(result.BODY.mname);

            $(result.BODY.categories).each(function(idx, category) {

                // $('#category').prop("selected", true);

                $("#category").val(`${category.category}`).prop("selected", true);

            });
            $(result.BODY.workdays).each(function(idx, workday) {

                $('.workdays'+`${workday.type}`).prop("checked", true);
            });

            $('#market').val(result.BODY.market).prop("selected", true);


            $('input[name=sthm]').val(result.BODY.sthm);
            $('input[name=ndhm]').val(result.BODY.ndhm);
            $('input[name=holidayInfo]').val(result.BODY.holidayInfo);
            $('input[name=sphone]').val(result.BODY.sphone);
            $('input[name=kakao]').val(result.BODY.kakao);
            $('input[name=address]').val(result.BODY.address);

            $('.sinfo').val(result.BODY.sinfo);

            var index = 0;
            var marketThumb = '';
            // type L = 약도
            // type N = 이미지
            if(result.BODY.files.length != 0){
                $(result.BODY.files).each(function(idx, file) {

                    index += 1;
                    var src = ServerUrl+'/file/download?fileName='+`${file.path}`;
                    var sfile = `${file.sfile}`;
                    var type = `${file.type}`;

                    if(index == 1){
                        marketThumb = src;
                    }

                    var fileadd = '<div class="img-wrap img-wrap'+idx+'">'
                                +'   <input type="hidden" name="sfile" value="'+sfile+'"/>'
                                +'   <input type="hidden" name="type" value="'+type+'"/>'
                                +'   <span class="remove-btn"><img src="../resource/image/manager/close-btn.png"></span>'
                                +'   <img class="preview" src="'+src+'">'
                                 +'</div>';
                    if(type == 'N'){
                        $(".store-img-upload-wrap").append(fileadd);
                    }
                    else{
                        $(".location-img-upload-wrap").append(fileadd);
                    }

                });

                $('.store-info-wrap img').attr('src', marketThumb);
            }
        },
        error: function(result) {
            console.log(result);

        }
    });
}
// 마이페이지 정보 불러오기
function getManagaer(mid){
    // 가게정보
    var data = {"member":mid};

    var userId;
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        async:false,
        url: ServerUrl+"/manager/getManager",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);

            $('.userId').text(result.BODY.id);
            $('.name').text(result.BODY.name);
            $('.phone').text(result.BODY.phone);
            $('.address').text(result.BODY.address);
        },
        error: function(result) {
            console.log(result);

        }
    });
}
// 플로리스트 관리자 계정 정보 수정 불러오기
function getmanagerDetail(mid){
    var data = {"member" : mid};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/manager/getManager",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);

            $('#member').val(result.BODY.member);
            $('.id').val(result.BODY.id);
            $('.name').val(result.BODY.name);
            $('.phone').val(result.BODY.phone);
            $('.email').val(result.BODY.email);
            $('.address').val(result.BODY.address);
        },
        error: function(result) {
            console.log(result);

        }
    });
}


// 가게 상품 정보 리스트 (관리자))
function getProductListManager(sid){
    var data = {"store":sid};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/manager/getProductList",
        data: JSON.stringify(data),
        success: function(result) {
            var menuHtml = '';
            $(result.BODY).each(function(idx, menu) {
                var src = '';
                if(menu.files.length != 0){
                    src = ServerUrl+'/file/download?fileName='+`${menu.files[0].path}`;
                } else {
                    src = 'https://via.placeholder.com/88x88';
                }
                menuHtml += `
                            <div class="item" onclick="location.href='/manager/productDetail?sid='+${menu.store}+'&mid='+${menu.menu}">
                                <div class="img-wrap">
                                         <img src="`+src+`" >
                                    </div>
                                    <div class="info-wrap">
                                        <p class="mname">${menu.mname}</p>
                                        <p class="minfo">${menu.minfo}</p>
                                        <p class="mprice">${menu.price}원</p>
                                        <a class="go-edit" href="/manager/editProduct?mid=${menu.menu}&cmd=2"><img src="../resource/image/manager/edit-icon.png">수정</a>
                                    </div>
                                </div>
                            </div>


                            `;

            });
            $("#menuList").html(menuHtml);

        },
        error: function(result) {
            console.log(result);
        }
    });
}
//가게 상품 상세 정보 (관리자)
function productInfoManager(mid){
    // 가게정보
    var data = {"menu":mid};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/manager/getProduct",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);


            $('.mname').text(result.BODY.mname);
            $('.mprice').text(result.BODY.price);
            $('.minfo').text(result.BODY.minfo);

            var src = '';
            if(result.BODY.files.length != 0){
                src = ServerUrl+'/file/download?fileName='+result.BODY.files[0].path;
                $('.product-img').attr('src', src);

            }
        },
        error: function(result) {
            console.log(result);

        }
    });
}
//가게 상품 수정 상세 정보 (관리자)
function productEditInfoAdm(mid){
    // 가게정보
    var data = {"menu":mid};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/manager/getProduct",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);

            $('input[name=menu]').val(result.BODY.menu);
            $('input[name=mname]').val(result.BODY.mname);
            $('input[name=minfo]').val(result.BODY.minfo);
            $('input[name=price]').val(result.BODY.price);

            // var src = '';
            // var imgHtml = '';
            // if(result.BODY.files.length != 0){
            //     src = ServerUrl+'/file/download?fileName='+result.BODY.files[0].path;
            //     imgHtml += `
            //                 <input id="img1" class="img-file" type="file" name="simg[]" />
            //                 <div class="controll-wrap">
            //                     <div class="img-preview">
            //                         <img class="preview" src="`+src+`" style="display: block"/>
            //                     </div>
            //                 </div>
            //                 `;
            //     $("#img").html(imgHtml);
            // }


            var src = '';
            var imgHtml = '';
            if(result.BODY.files.length != 0){

                $('.add-img').hide()
                src = ServerUrl+'/file/download?fileName='+result.BODY.files[0].path;
                var mfile = result.BODY.files[0].mfile ;

                var fileadd = '<div class="img-wrap img-wrap">'
                    +'   <input id="img1" class="img-file" style="display: none" type="file" name="simg[]" id="img1" /> '
                    +'   <input type="hidden" class="mfile" name="mfile" value="'+ mfile +'" /> '

                    +'   <span class="remove-btn"><img src="../resource/image/manager/close-btn.png"></span>'
                    +'   <img class="preview" src="'+ src +'">'
                    +'</div>';
                $(".img-upload-wrap").append(fileadd);
            }



            // if(result.BODY.files.length != 0){
            //     $(result.BODY.files).each(function(idx, file) {
            //         var src = ServerUrl+'/file/download?fileName='+`${file.path}`;
            //
            //         var fileadd = '<div class="img-wrap img-wrap'+idx+'">'
            //             +'   <input type="hidden" name="sfile" value="'+sfile+'"/>'
            //             +'   <input type="hidden" name="type" value="'+type+'"/>'
            //             +'   <input class="img-file" style="display: none" type="file" accept="image/*" name="simg'+idx+'" />'
            //             +'   <span class="remove-btn"><img src="../resource/image/manager/close-btn.png"></span>'
            //             +'   <img class="preview" src="+src+">'
            //             +'</div>';
            //         $(".img-upload-wrap").append(fileadd);
            //     });
            // }


        },
        error: function(result) {
            console.log(result);

        }
    });
}
//가게 메뉴 삭제 (관리자)
function menuDelAdm(mid){

    var result = confirm('선택하신 메뉴가 삭제됩니다. 삭제하시겠습니까?');

    if(result){
        var data = {"menu":mid};
        $.ajax({
            type: "POST",
            headers: {
                "Content-Type": "application/json",
                "X-HTTP-Method-Override": "POST"
            },
            url: ServerUrl+"/store/deleteMenu",
            data: JSON.stringify(data),
            success: function(result) {
                console.log(result);
                alert('삭제되었습니다.');
                window.location.reload();
            },
            error: function(result) {
                console.log(result);

            }
        });
    }
}
// //가게 상세정보 (관리자)
// function managerStoreInfo(sid){
//     // 가게정보
//     var data = {"store":sid};
//     $.ajax({
//         type: "POST",
//         headers: {
//             "Content-Type": "application/json",
//             "X-HTTP-Method-Override": "POST"
//         },
//         url: ServerUrl+"/manager/getStore",
//         data: JSON.stringify(data),
//         success: function(result) {
//             console.log(result);
//
//
//             //내가게 축약정보
//             $('.store-name').text(result.BODY.sname);
//             $(result.BODY.categories).each(function(idx, category) {
//                 $('.category').text(`${category.name}`);
//             });
//             var sInfo;
//             if(result.BODY.sinfo.length >= 30){
//                 sInfo = result.BODY.sinfo.substr(0,30)+"...";
//             } else {
//                 sInfo = result.BODY.sinfo;
//             }
//             $('.store-info').text(sInfo);
//             $('.store-info-wrap').click(function(){
//                 $(location).attr('href', '/manager/editStore?sid='+sid);
//             });
//             //-------------------------------------------------------------//
//
//
//             // 가게 상세 입렵정보
//             $('input[name=store]').val(result.BODY.store);
//             $('input[name=sname]').val(result.BODY.sname);
//             $('input[name=mname]').val(result.BODY.mname);
//
//             $(result.BODY.categories).each(function(idx, category) {
//
//                 $("#category").val(`${category.categories}`).prop("selected", true);
//
//             });
//             $(result.BODY.workdays).each(function(idx, workday) {
//
//                 $('.workdays'+`${workday.type}`).prop("checked", true);
//             });
//
//             $('input[name=sthm]').val(result.BODY.sthm);
//             $('input[name=ndhm]').val(result.BODY.ndhm);
//             $('input[name=holidayInfo]').val(result.BODY.holidayInfo);
//             $('input[name=sphone]').val(result.BODY.sphone);
//             $('input[name=kakao]').val(result.BODY.kakao);
//             $('input[name=address]').val(result.BODY.address);
//             $('.sinfo').val(result.BODY.sinfo);
//
//             // type L = 약도
//             // type N = 이미지
//             if(result.BODY.files.length != 0){
//                 $(result.BODY.files).each(function(idx, file) {
//                     var src = ServerUrl+'/file/download?fileName='+`${file.path}`;
//                     var sfile = `${file.sfile}`;
//                     var type = `${file.type}`;
//                     var chk = '';
//                     if(type == 'L'){
//                         chk = 'checked';
//                     }
//
//                     var fileadd = '<div class="img-wrap img-wrap'+idx+'">'
//                         +'   <input type="hidden" name="sfile" value="'+sfile+'"/>'
//                         +'   <input type="hidden" name="type" value="'+type+'"/>'
//                         +'   <input class="img-file" style="display: none" type="file" accept="image/*" name="simg'+idx+'" />'
//                         +'   <span class="remove-btn"><img src="../resource/image/manager/close-btn.png"></span>'
//                         +'   <img class="preview" src="+src+">'
//                         +'</div>';
//                     $(".img-upload-wrap").append(fileadd);
//                 });
//             }
//         },
//         error: function(result) {
//             console.log(result);
//
//         }
//     });
// }
// 쿠폰 보유 리스트 (관리자)
function getCouponListManager(sid){
    var data = {"store":sid};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/manager/getCouponList",
        data: JSON.stringify(data),
        success: function(result) {
            var couponHtml = '';
            var sum = 0;
            $(result.BODY).each(function(idx, coupon) {
                sum += 1;
                //쿠폰 이름
                var cname;
                if(`${coupon.cname}`.length >= 15){
                    cname = `${coupon.cname}`.substr(0,15)+"...";
                } else {
                    cname = `${coupon.cname}`;
                }

                //쿠폰 설명
                var cinfo;
                if(`${coupon.cinfo}`.length >= 30){
                    cinfo = `${coupon.cinfo}`.substr(0,30)+"...";
                } else {
                    cinfo = `${coupon.cinfo}`;
                }

                var bgndt = `${coupon.bgndt}`.replace("-",".");
                var nddt = `${coupon.nddt}`.replace("-",".");

                couponHtml += `
                            <div class="item" onclick="location.href='/manager/couponDetail?cid=${coupon.coupon}&sid=${coupon.store}'">
                                <p class="cname">`+cname+`</p>
                                <p class="cinfo">`+cinfo+`</p>
                                <p class="cdate">`+ bgndt +` ~ `+ nddt +`</p>
                            </div>
                            
                            `;
            });
            $("#coupon-List").html(couponHtml);

            $('#coupon-total').text(sum);
        },
        error: function(result) {
            console.log(result);
        }
    });
}
//가게 쿠폰 상세 정보 (관리자)
function getCouponDetail(cid){
    // 가게정보
    var data = {"coupon":cid};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/manager/getCoupon",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);

            var ctype = '';
            var disprice = '';

            if(result.BODY.ctype == 1){
                ctype = "가격 할인";
                disprice = result.BODY.stanprice.replace(/\B(?=(\d{3})+(?!\d))/g, ",") +"원 이상 " + result.BODY.disprice.replace(/\B(?=(\d{3})+(?!\d))/g, ",") + "원 할인"
            }
            else{
                ctype = "배달비 무료";
                disprice = "배달비 무료";
            }



            $('.cname').text(result.BODY.cname);
            $('.ctype').text(ctype);
            $('.bgndt').text( result.BODY.bgndt.substr(0,10).replace(/-/gi,"."));
            $('.nddt').text( result.BODY.nddt.substr(0,10).replace(/-/gi,"."));
            $('.disprice').text(disprice);
            $('.cinfo').text(result.BODY.cinfo);
            $('.cnote').text(result.BODY.cnote);

            $('input[name=coupon]').val(result.BODY.coupon);
            $("select").val(result.BODY.ctype).prop("selected", true);
            $('input[name=cname]').val(result.BODY.cname);
            $('input[name=ctype]').val(ctype);
            $('input[name=bgndt]').val(result.BODY.bgndt);
            $('input[name=nddt]').val(result.BODY.nddt);
            $('input[name=stanprice]').val(result.BODY.stanprice);
            $('input[name=disprice]').val(result.BODY.disprice);

        },
        error: function(result) {
            console.log(result);

        }
    });
}


// 가게 리뷰 리스트 (관리자)
function getReviewListManager(sid){
    var data = {"store":sid};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/manager/getReviewList",
        data: JSON.stringify(data),
        success: function(result) {
            var reviewHtml = '';
            var total = 0;
            var sum = 0;
            var num = 0;
            var userId;
            $(result.BODY).each(function(idx, review) {

                var regdate = `${review.regDate}`.substr(0,10);
                sum += parseFloat(`${review.rate}`);
                num += 1;

                userId = `${review.member}`

                reviewHtml += `
                           <div class="item">
                                <div class="top-wrap">
                                    <div class="rate-wrap">
                                        <p><img src="../resource/image/store/rate-icon.png"> ${review.rate}</p>
                                    </div>
                                    <div class="user-info-wrap">
                                        <p class="id">`+reviewUserId(userId)+`</p>
                                        <p class="regdate">`+regdate+`</p>
                                    </div>
                                </div>
                                <div class="bottom-wrap">
                                    <p>${review.contents}</p>
                                </div>
                            </div>
                            `;
            });
            $("#reviewHtml").html(reviewHtml);



            if (isNaN(total)) { // 값이 없어서 NaN값이 나올 경우

                total = 0;

            }
            $('.total-rate-wrap .total').text(total.toFixed(1));
            $('.total-rate-wrap .review-num').text(num+' (0 new)');
            $('#total').text(total.toFixed(1));
            $('#review-num').text(num+' (0 new)');
        },
        error: function(result) {
            console.log(result);
        }
    });
}



// 가게 방문자 가져오기 (관리자)
function getStoreVisitor(sid){
    var data = {"store":sid};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/manager/getStore",
        data: JSON.stringify(data),
        success: function(result) {
            $('.visitor').text(result.BODY.visit + " 명");

        },
        error: function(result) {
            console.log(result);
        }
    });
}

// 로그인버튼 초기화
function loginCheck(){
    var data = {};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/main/getLoginInfo",
        data: JSON.stringify(data),
        success: function(result) {
            if(result.BODY != null){
                $('#login').hide();
                $('#logout').show();
            } else {
                $('#login').show();
                $('#logout').hide();
            }
        },
        error: function(result) {
            console.log(result);
        }
    });
}

function fnLogout(){
    if(confirm('로그아웃 하시겠습니까?')){
        sessionStorage.removeItem("mid");
        window.location.href = '/logout';
    }
}

function getLoginInfo(){
    var data = {};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/main/getLoginInfo",
        data: JSON.stringify(data),
        success: function(result) {
            if(result.BODY != null){
                if(result.BODY.role == 'ROLE_USER'){
                    $(".user").show();
                    $(".store").hide();
                } else {
                    $(".user").hide();
                    $(".store").show();
                }
            } else {
                //window.location.href = '/login';
                $(".user").show();
                $(".store").hide();
            }
        },
        error: function(result) {
            console.log(result);
        }
    });
}

//상단 베너 호출
function banneerList(type, imgHtml){
    // 가게정보
    var data = {'type' : type};
    $.ajax({
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-HTTP-Method-Override": "POST"
        },
        url: ServerUrl+"/main/getBannerList",
        data: JSON.stringify(data),
        success: function(result) {
            console.log(result);

            showBanner(result.BODY, type, imgHtml);
        },
        error: function(result) {
            console.log(result);
        }
    });
}

function showBanner(fileList, type, imgHtml){
    switch (type) {
        case 'T' :
            if(fileList.length != 0) {
                var bannerHtml = '';
                $(fileList).each(function (idx, file) {
                    var src = ServerUrl + '/file/download?fileName=' + `${file.path}`;
                    bannerHtml += `
                    <li class="swiper-slide">
                        <img src="` + src + `" width="420" height="180" onclick="location.href='${file.link}'">
                    </li>
                            `;
                });
                bannerHtml += imgHtml;
                $("#eventSlide").html(bannerHtml);

                var slide = new Swiper('.slide-banner-wrap', {
                    slidesPerView : '1',
                    pagination : {
                        el : '.swiper-pagination',
                        clickable : true,
                    },
                    autoplay: {
                        delay: 2000,
                        disableOnInteraction: true // 쓸어 넘기거나 버튼 클릭 시 자동 슬라이드 정지.
                    },
                    loop: true
                });
            }
            break;
        case 'M' :
            if(fileList.length != 0) {
                var bannerHtml = '';
                $(fileList).each(function (idx, file) {
                    var src = ServerUrl + '/file/download?fileName=' + `${file.path}`;
                    bannerHtml += `
                <img src="`+src+`" width="420" height="103">
                            `;
                });
                $("#middleBanner").html(bannerHtml);
            }
            break;
    }
}
