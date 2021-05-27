package app.lonzh.baselibrary.livedata;

/**
 * @ProjectName: lisper
 * @Description: 描述
 * @Author: Lisper
 * @CreateDate: 5/18/21 3:27 PM
 * @UpdateUser: Lisper：
 * @UpdateDate: 5/18/21 3:27 PM
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class UnPeekLiveData<T> extends ProtectedUnPeekLiveData<T> {

    @Override
    public void setValue(T value) {
        super.setValue(value);
    }

    @Override
    public void postValue(T value) {
        super.postValue(value);
    }

    public static class Builder<T> {

        /**
         * 是否允许传入 null value
         */
        private boolean isAllowNullValue;

        public Builder<T> setAllowNullValue(boolean allowNullValue) {
            this.isAllowNullValue = allowNullValue;
            return this;
        }

        public UnPeekLiveData<T> create() {
            UnPeekLiveData<T> liveData = new UnPeekLiveData<>();
            liveData.isAllowNullValue = this.isAllowNullValue;
            return liveData;
        }
    }
}
