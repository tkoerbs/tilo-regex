package de.tilo_koerbs.regex.internal;

public enum LogLevel {
    TRACE(6),
    DEBUG(5),
    INFO(4),
    WARN(3),
    ERROR(2),
    FATAL(1),
    NOTHING(0);
    
    protected int levelCode;
    
    LogLevel(int levelCode)
    {
        this.levelCode = levelCode;
    }
    
    public int getLevelCode()
    {
        return levelCode;
    }
}
