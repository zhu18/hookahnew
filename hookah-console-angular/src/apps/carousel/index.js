import "../../common/common";
import carouselRouting from "./carousel.routing";

const MODULE_NAME = 'Hookah';
let app = angular.module(MODULE_NAME, [
	'ui.router',
	'Common',
	'ui.bootstrap',
]);
app.config(carouselRouting);

app.run(function ($rootScope, $state) {
	$rootScope.currentProductId = 'carousel';
	$rootScope.config = {
		"navScene": 'main',
		"isSidebarFold": false,
		"disableNavigation": false,
		"hideSidebar": false,
		"sidebar": "full",
		"productNavBar": 'col-1'
	};
	$rootScope.navConfig = {
		"product": {
			"show": true,
			"folded": false
		},
		"service": {
			"show": true,
			"folded": false
		},
		"system": {
			"show": true,
			"folded": false
		}
	};
	$rootScope.config.title = '广告管理';
	$rootScope.config.mainNav = [
		{
			"title": "首页轮播",
			"url": "carousel.search"
		},
		{
			"title": "商详页",
			"url": "carousel.ad"
		}

	]
});

export default MODULE_NAME;