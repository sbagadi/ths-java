package ths.spring;

import static ths.commons.lang.Assert.*;

/**
 * 用来索引BeanElement的key。
 *
 */
class BeanElementKey implements Comparable<BeanElementKey> {
    private final String name;
    private final BeanElementType type;

    public BeanElementKey(String name, BeanElementType type) {
        this.name = assertNotNull(name, "name");
        this.type = assertNotNull(type, "type");
    }

    public String getName() {
        return name;
    }

    public BeanElementType getType() {
        return type;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (name == null ? 0 : name.hashCode());
        result = prime * result + (type == null ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (!(obj instanceof BeanElementKey)) {
            return false;
        }

        BeanElementKey other = (BeanElementKey) obj;

        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }

        if (type == null) {
            if (other.type != null) {
                return false;
            }
        } else if (!type.equals(other.type)) {
            return false;
        }

        return true;
    }

    public int compareTo(BeanElementKey o2) {
        int result = name.compareTo(o2.name);

        if (result == 0) {
            result = type.compareTo(o2.type);
        }

        return result;
    }

    @Override
    public String toString() {
        return "ContributionKey[" + name + ", " + type + "]";
    }
}
