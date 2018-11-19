(function($)
{
	/**
	 * MC框架UI组件widget基类
	 * 提供namespace、widgetname、widgetFullName访问接口
	 * 提供element元素基础检查
	 * 静态参数访问机制
	 */
	$.widget("mc.BaseWidget",
	{
		version: "1.12.1",
		defaultElement: "<div>",
		/**
		 * @override
		 * 构造接口，只执行一次
		 */
		_create : function()
		{
			/**
			 * 检查jQuery对象
			 */
			mc.assert_jquery(this.element, "", "element不是jQuery对象，创建" + this.getWidgetFullName() + "组件失败");

			this.element.data("mc-wg-fullname", this.getWidgetFullName());
			this.element.data("mc-wg-name", this.getWidgetName());
		},
		/**
		 * @override
		 * 初始化接口，可执行多次
		 */
		_init : function()
		{
			this._super();
		},
		/**
		 * @override
		 * 析构接口
		 */
		_destroy : function()
		{
			this._super();
		},
		/**
		 * 访问namespace
		 */
		getNamespace : function()
		{
			return this.namespace;
		},
		/**
		 * 访问widgetName
		 */
		getWidgetName : function()
		{
			return this.widgetName;
		},
		/**
		 * 访问widgetFullName
		 */
		getWidgetFullName : function()
		{
			return this.widgetFullName;
		},
		/**
		 * 通用静态参数
		 */
		optionsConst : {},
		/**
		 * 创建基础参数，把静态参数覆盖到初始化参数上
		 */
		createBaseOptions : function()
		{
			return $.extend(true, {}, this.options, this.optionsConst);
		}
	});
})(jQuery);