function tabsSwipeCtrlFn() {
    var responsive = this;
    responsive.ngIncludeTemplates = [{ index: 0, name: 'first', url: 'index.html' }, { index: 1, name: 'second', url: '/register' }];
    responsive.selectPage = selectPage;

    /**
    * Initialize with the first page opened
    */
    responsive.ngIncludeSelected = responsive.ngIncludeTemplates[0];

    /**
    * @name selectPage
    * @desc The function that includes the page of the indexSelected
    * @param indexSelected the index of the page to be included
    */
    function selectPage(indexSelected) {
        if (responsive.ngIncludeTemplates[indexSelected].index > responsive.ngIncludeSelected.index) {
            responsive.moveToLeft = false;
        } else {
            responsive.moveToLeft = true;
        }
        responsive.ngIncludeSelected = responsive.ngIncludeTemplates[indexSelected];
    }
}

var app = angular.module('myApp', ['ngAnimate', 'ngTouch'])
    .controller('tabsSwipeCtrl', tabsSwipeCtrlFn);
