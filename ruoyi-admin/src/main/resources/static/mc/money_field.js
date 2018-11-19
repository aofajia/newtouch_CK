/**
 * 金额输入组件
 * 基于jQuery-UI.widget
 * 只允许输入金额，自动保留两位小数
 * 显示为靠右对齐，千分位、两位小数，人民币符号
 * 允许设置货币符号样式、千分位开关
 */
(function($)
{
	$.widget("mc.MoneyField", $.mc.BaseWidget,
	{
		version: "1.12.1",
		defaultElement : "<input>",
		/**
		 * 默认设置
		 */
		options :
		{
			currency_sign : "¥",	/* 货币符号 */
			decimal : 2,			/* 小数位数据 */
			thousands : true		/* 千分位开关 */
		},
		/**
		 * @override
		 * 构造接口，只执行一次
		 */
		_create : function()
		{
			this._super();

			this.element.bind("input propertychange", this.valueChangeEvent);
			
			this.element.focus($.proxy(this.focusEvent, this));

			this.element.blur($.proxy(this.blurEvent, this));
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
		_destory : function()
		{
			this._super();
		},
		/**
		 * 属性值变化事件，约束输入
		 */
		valueChangeEvent : function(event)
		{
			var input = event.target;
			if (input.value == '-' || input.value == input.oldvalue)
			{
				return;
			}

			var numvalue = Number(input.value);
			if (isNaN(numvalue))
			{
				/**
				 * 不合法，恢复原值
				 */
				if (input.oldvalue == undefined || input.oldvalue == null)
				{
					input.value = 0;
				}
				else
				{
					input.value = input.oldvalue;
				}
			}
			else
			{
				/**
				 * 合法数字值
				 */
				input.oldvalue = input.value;
			}
		},
		/**
		 * 获得焦点事件，去除金额符号和千分位
		 */
		focusEvent : function(event)
		{
			var value = this.displayToValue(this.element.val());
			this.element.val(value);
		},
		/**
		 * 失去焦点事件，增加金额符号和千分位
		 */
		blurEvent : function(event)
		{
			var value = this.valueToDisplay(this.element.val());
			this.element.val(value);
		},
		/**
		 * 显示值转为实际值
		 */
		displayToValue : function(display)
		{
			var value = display.replace(/,|\s/g, '');
			value = value.replace(this.options.currency_sign, "");
			return value;
		},
		/**
		 * 实际值转为显示值
		 * 增加千分位，四舍五入保留2位，增加金额符号
		 */
		valueToDisplay : function(value)
		{
			/* 获取小数型数据 */
			var s = value; 
			var b = parseFloat(s);
			if ( (! _.isNumber(b)) || _.isNaN(b))
			{
				b = 0;
			}

			s = Number(b).toFixed(this.options.decimal);
			s += "";
			if (this.options.decimal > 0)
			{
				if (s.indexOf(".") == -1)
				{
					s += ".0"; 	/* 如果没有小数点，在后面补个小数点和0 */
				}
				
				var arrayS = s.split(".");
				for (var i = 0; i < (this.options.decimal - $.trim(arrayS[1]).length); i++)
				{
					s += "0";
				}
			}

			if (this.options.thousands)
			{
				while (/\d{4}(\.|,)/.test(s))
				{
					/* 符合条件则进行替换 */
					s = s.replace(/(\d)(\d{3}(\.|,))/, "$1,$2"); /* 每隔3位添加一个 */
				}
			}

			s = this.options.currency_sign + s;
			return s;
		},
		/**
		 * 访问实际值
		 */
		value : function(value)
		{
			if (value == undefined)
			{
				return this.getValue();
			}
			else
			{
				this.setValue(value);

				return this;
			}
		},
		/**
		 * 获取实际值
		 */
		getValue : function()
		{
			return Number(this.displayToValue(this.element.val()));
		},
		/**
		 * 设置实际值
		 */
		setValue : function(value)
		{
			var value = this.valueToDisplay(value);
			this.element.val(value);

			return this;
		}
	});
})(jQuery);