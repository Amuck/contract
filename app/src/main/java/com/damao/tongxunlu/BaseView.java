
package com.damao.tongxunlu;

public interface BaseView<T extends BasePresenter> {

    void setPresenter(T presenter);

}
